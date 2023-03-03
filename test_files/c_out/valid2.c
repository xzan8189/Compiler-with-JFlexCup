#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <string.h>

//funzioni di supporto
char * intToString(int n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%d", n);
	return dest;
}

char * doubleToString(double n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%lf", n);
	return dest;
}

char * boolToString(bool b) {
	char * dest = malloc(sizeof(char)*2);
	if(b == true)
		dest = "true";
	else
		dest = "false";
	return dest;
}

char * strConcat(char * str1, char * str2) {
	char * dest = malloc(sizeof(char)*256);
	strcat(dest, str1);
	strcat(dest, str2);
	return dest;
}

//dichiarazioni variabili globali

//dichiarazioni funzioni
int print_menu(){
	int choose;

	printf("%s", "Scegli l'operazione da svolgere per continuare");
	printf("\n");

	printf("%s", "\t(1) Somma di due numeri");
	printf("\n");

	printf("%s", "\t(2) Moltiplicazione di due numeri");
	printf("\n");

	printf("%s", "\t(3) Divisione intera fra due numeri positivi");
	printf("\n");

	printf("%s", "\t(4) Elevamento a potenza");
	printf("\n");

	printf("%s", "\t(5) Successione di Fibonacci (ricorsiva)");
	printf("\n");

	printf("%s", "\t(6) Successione di Fibonacci (iterativa)");
	printf("\n");

	printf("%s", "\t(0) Esci");
	printf("\n");

	printf("--> ");
	scanf(" %d", &choose);
	fflush(stdin);

	return choose;

}

void do_sum(){
	double op1, op2;

	printf("%s", "\n(1) SOMMA\n");
	printf("\n");

	printf("Inserisci il primo operando: ");
	scanf(" %lf", &op1);
	fflush(stdin);

	printf("Inserisci il secondo operando: ");
	scanf(" %lf", &op2);
	fflush(stdin);

	printf("%s", "");
	printf("\n");

	printf("%s", strConcat(strConcat(strConcat(strConcat(strConcat("La somma tra ", doubleToString(op1)), " e "), doubleToString(op2)), " vale "), doubleToString(op1 + op2)));
	printf("\n");

}

void do_mul(){
	double op1, op2;

	printf("%s", "\n(2) MOLTIPLICAZIONE");
	printf("\n");

	printf("\nInserisci il primo operando: ");
	scanf(" %lf", &op1);
	fflush(stdin);

	printf("Inserisci il secondo operando: ");
	scanf(" %lf", &op2);
	fflush(stdin);

	printf("%s", "");
	printf("\n");

	printf("%s", strConcat(strConcat(strConcat(strConcat(strConcat("La moltiplicazione tra ", doubleToString(op1)), " e "), doubleToString(op2)), " vale "), doubleToString(op1 * op2)));
	printf("\n");

}

void do_div_int(){
	int op1, op2;

	printf("%s", "\n(3) DIVISIONE INTERA");
	printf("\n");

	printf("\nInserisci il primo operando: ");
	scanf(" %d", &op1);
	fflush(stdin);

	printf("Inserisci il secondo operando: ");
	scanf(" %d", &op2);
	fflush(stdin);

	printf("%s", "");
	printf("\n");

	printf("%s", strConcat(strConcat(strConcat(strConcat(strConcat("La divisione intera tra ", intToString(op1)), " e "), intToString(op2)), " vale "), intToString(op1 / op2)));
	printf("\n");

}

void do_pow(){
	double op1, op2;

	printf("%s", "\n(4) POTENZA");
	printf("\n");

	printf("\nInserisci la base: ");
	scanf(" %lf", &op1);
	fflush(stdin);

	printf("Inserisci l'esponente: ");
	scanf(" %lf", &op2);
	fflush(stdin);

	printf("%s", "");
	printf("\n");

	printf("%s", strConcat(strConcat(strConcat(strConcat(strConcat("La potenza di ", doubleToString(op1)), " elevato a "), doubleToString(op2)), " vale "), doubleToString(pow(op1, op2))));
	printf("\n");

}

int recursive_fib(int n){

	if(n == 1){

		return 0;

	}
;

	if(n == 2){

		return 1;

	}
;

	return 	recursive_fib(n - 1) + 	recursive_fib(n - 2);

}

int iterative_fib(int n){

	if(n == 1){

		return 0;

	}
;

	if(n == 2){

		return 1;

	}
;

	if(n > 2){
		int i = 3, res = 1, prev = 0;

		while(i <= n){
			int tmp = res;

			res = res + prev;

			prev = tmp;

			i = i + 1;

		}
;

		return res;

	}
;

	return  -1;

}

void do_fib(bool recursive){
	int n;
	char * message = malloc (sizeof(char) * 128);

	printf("%s", "\n(5) FIBONACCI");
	printf("\n");

	printf("\nInserisci n: ");
	scanf(" %d", &n);
	fflush(stdin);

	printf("%s", "");
	printf("\n");

	strcpy(message, strConcat(strConcat("Il numero di Fibonacci in posizione ", intToString(n)), " vale "));

	if(recursive){

		strcpy(message, strConcat(message, intToString(		recursive_fib(n))));

	}
	else {

		strcpy(message, strConcat(message, intToString(		iterative_fib(n))));

	};

	printf("%s", message);
	printf("\n");

}

void do_operation(int choose){

	if(choose == 1){

		do_sum();

	}
	else {

		if(choose == 2){

			do_mul();

		}
		else {

			if(choose == 3){

				do_div_int();

			}
			else {

				if(choose == 4){

					do_pow();

				}
				else {

					if(choose == 5){

						do_fib(true);

					}
					else {

						if(choose == 6){

							do_fib(false);

						}
;

					};

				};

			};

		};

	};

}

void print_continua(bool * continua){
	char * in = malloc (sizeof(char) * 128);

	printf("Vuoi continuare? (s/n) --> ");
	scanf(" %s", in);
	fflush(stdin);

	if(strcmp(in, "s") ==  0){

		* continua = true;

	}
	else {

		* continua = false;

	};

}


//main
int main(){
	int 	choose = 0;
	bool 	continua = true;

	while(continua){

		choose = 		print_menu();

		if(choose == 0){

			continua = false;

		}
		else {

			do_operation(choose);

			print_continua(&continua);

		};

	}
;

}