
package br.com.formiga;


import java.util.*;


public class Main {

	private static final Character PLUS = '+';
	private static final Character MINUS = '-';
	private static final Character MULTIPLICATION = '*';
	private static final Character DIVISION = '/';
	private static final Character CLOSE_PARENTHESIS = ')';
	private static final Character DOT = '.';


	public static void main (String[] args) {

		System.out.print("Digite a expressao: ");
		Scanner s = new Scanner(System.in);

		Main.calculator(s.nextLine());
	}

	public static String calculator(String str) {

		Double total = 0.0;
		Stack<String> operators = new Stack<>();
		Stack<Double> numbers = new Stack<>();

		String expressions[] = str.split("\\(");

		for (int i = expressions.length - 1; i >= 0; i --) {

			for (int k = 0; k <= expressions[i].length(); k ++) {

				if ((k >= expressions[i].length()) || (Character.toString(Main.CLOSE_PARENTHESIS).equals(Character.toString(expressions[i].charAt(k))))) {

					while (!numbers.isEmpty()) {

						if (numbers.size() == 1) {
							total = numbers.pop();

							break;
						}

						Double second;
						Double first;
						String operator = operators.pop();

						if ((!operators.isEmpty()) && (Character.toString(Main.MINUS).equals(operators.peek()))) {

							if (Character.toString(Main.MINUS).equals(operator)) {

								second = numbers.pop();
								first = numbers.pop();

								operator = Character.toString(Main.PLUS);
							} else {

								first = numbers.pop();
								second = numbers.pop();

								operator = operators.pop();

								operators.push(Character.toString(Main.PLUS));
							}
						} else {
							second = numbers.pop();
							first = numbers.pop();
						}

						numbers.push(Main.calculate(operator, first, second));
					}

					if (i > 0) {

						if ((i < expressions.length) && (expressions[i - 1].length() > 0)) {

							String characterToCompare = Character.toString(expressions[i - 1].charAt(expressions[i - 1].length() - 1));

							if ((Main.isInteger(characterToCompare)) || (Character.toString(Main.CLOSE_PARENTHESIS).equals(characterToCompare))) {
								expressions[i - 1] = expressions[i - 1].concat(Character.toString(Main.MULTIPLICATION));
							}
						}

						expressions[i - 1] = expressions[i - 1].concat(total.toString());
					}

					if ((expressions[i].length() > k) && (i > 0)) {
						int index = k + 1;
						expressions[i - 1] = expressions[i - 1].concat(expressions[i].substring(index));
					}

					break;
				}

				String character = Character.toString(expressions[i].charAt(k));

				if (Main.isInteger(character)) {

					String number = character;

					while ((k + 1 < expressions[i].length()) &&
							((Main.isInteger(Character.toString(expressions[i].charAt(k + 1)))) || (Character.toString(Main.DOT).equals(Character.toString(expressions[i].charAt(k + 1)))))) {
						number = number.concat(Character.toString(expressions[i].charAt(++ k)));
					}

					numbers.push(Double.parseDouble(number));
				} else if ((character.equals(Character.toString(Main.PLUS))) || (character.equals(Character.toString(Main.MINUS)))) {
					operators.push(character);
				} else if ((character.equals(Character.toString(Main.MULTIPLICATION))) || (character.equals(Character.toString(Main.DIVISION)))) {

					String number = Character.toString(expressions[i].charAt(++ k));

					while ((k + 1 < expressions[i].length()) &&
							((Main.isInteger(Character.toString(expressions[i].charAt(k + 1)))) || (Character.toString(Main.DOT).equals(Character.toString(expressions[i].charAt(k + 1)))))) {
						number = number.concat(Character.toString(expressions[i].charAt(++ k)));
					}

					numbers.push(Main.calculate(character, numbers.pop(), Double.valueOf(number)));
				}
			}
		}

		System.out.println("Total = " + total.toString());

		return total.toString();
	}

	public static boolean isInteger(String input) {

		if (input == null) return false;

		try {
			Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static Double calculate(String operator, Double number1, Double number2) {

		if (operator.equals(Character.toString(Main.PLUS))) {
			return number1 + number2;
		} else if (operator.equals(Character.toString(Main.MINUS))) {
			return number1 - number2;
		} else if (operator.equals(Character.toString(Main.MULTIPLICATION))) {
			return number1 * number2;
		} else {
			return number1 / number2;
		}
	}

}