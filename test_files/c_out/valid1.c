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
int c = 1;

//dichiarazioni funzioni
double sommac(int a, double b, char * size){
	double result;

	result = a + b + c;

	if(result > 100){
		char * valore = malloc (sizeof(char) * 128);
		strcpy(valore, "grande");

		strcpy(size, valore);

	}
	else {
		char * valore = malloc (sizeof(char) * 128);
		strcpy(valore, "piccola");

		strcpy(size, valore);

	};

	return result;

}

void stampa(char * messaggio){
	int 	i = 1;

	while(i <= 4){
		int 		incremento = 1;

		printf("%s", "");
		printf("\n");

		i = i + incremento;

	}
;

	printf("%s", messaggio);
	printf("\n");

}


//main
int main(){
	int 	a = 1;
	double 	b = 2.2;
	char * taglia = malloc (sizeof(char) * 128);
	char * ans = malloc (sizeof(char) * 128);
	strcpy(ans, "no");
	double risultato = 	sommac(a, b, taglia);

	stampa(strConcat(strConcat(strConcat(strConcat(strConcat(strConcat(strConcat("la somma di ", intToString(a)), " e "), doubleToString(b)), " incrementata di "), intToString(c)), " è "), taglia));

	stampa(strConcat("ed è pari a ", doubleToString(risultato)));

	printf("%s", "vuoi continuare? (si/no)");
	printf("\t");

	scanf(" %s", ans);
	fflush(stdin);

	while(strcmp(ans, "si") ==  0){

		printf("inserisci un intero:");
		scanf(" %d", &a);
		fflush(stdin);

		printf("inserisci un reale:");
		scanf(" %lf", &b);
		fflush(stdin);

		risultato = 		sommac(a, b, taglia);

		stampa(strConcat(strConcat(strConcat(strConcat(strConcat(strConcat(strConcat("la somma di ", intToString(a)), " e "), doubleToString(b)), " incrementata di "), intToString(c)), " è "), taglia));

		stampa(strConcat(" ed è pari a ", doubleToString(risultato)));

		printf("vuoi continuare? (si/no):\t");
		scanf(" %s", ans);
		fflush(stdin);

	}
;

	printf("%s", "");
	printf("\n");

	printf("%s", "ciao");

}