#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <locale.h>
#include <string.h>
#include <stdlib.h>
#include <malloc.h>
#include <stdbool.h>
#include <ctype.h>
//Задание номер 7

void deleteSymbol(char str[], int pos);

void trim(char str[]);

void checkWord(char* str, char* token);


void main() {
	setlocale(LC_ALL,"Rus");
	char forbidenSymbols[] = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789!@#№$;%^:?&*()-_=+{}[]|\\;:\"\'/,.`~";

	char inputStr[255];
	char bufStr[255];
	char lastWord[64];
	char wordsArr[64][64];
	char* token = {NULL};

	printf("Введите строку:\n");
	do
	{
		gets(inputStr);
		//trim(inputStr);
		//printf("Строка без пробелов : % s", inputStr);
		if (strlen(inputStr) == 0 || inputStr[0] == '\0')
		{
			printf("Пустая строка");
		}
	} while (strlen(inputStr) == 0 || inputStr[0] == '\0');
  
	strcpy(bufStr, inputStr);
	printf("\nОтформатированная строка:\n%s\n", bufStr);

	int j = 0;
	for (int i = strlen(bufStr) - 1; i > 0; i--)  //Поиск последнего слова
	{	
		if (bufStr[i] != ' ') {
			lastWord[j] = bufStr[i];
			j++;
		} 
		else
		{
			break;
		}
	}
	_strrev(lastWord);
	printf("\nПоследнее слово:\n%s\n", lastWord);

	
	//Напечатать слова, отличные от последнего слова, если они удовлетворяют следующему условию: 
	//слово совпадает с конечным отрезком латинского алфавита (z, yz, xyz и т.д.).
	printf("\nП.1\n");
	printf("------------------------------\n");
	//решение П.1

	printf("------------------------------\n");

	//Напечатать все слова, отличные от последнего слова, удаляя все гласные буквы
	printf("\nП.2\n");
	printf("------------------------------\n");
	//решение П.2

	printf("------------------------------\n\n");
	
}

void trim(char str[]) {
	for (int i = 0; i < strlen(str); i++)        //Удаление лишних пробелов
	{
		if ((str[i] == ' ' && str[i + 1] == ' '))
		{
			deleteSymbol(str, i);
			i--;
		}
	}
	if (str[0] == ' ') {
		deleteSymbol(str, 0);
	}
	if (str[strlen(str) - 1] == ' ') {
		deleteSymbol(str, strlen(str) - 1);
	}
}

void deleteSymbol(char str[], int pos) {
	for (int i = pos; i < (strlen(str) - 1); ++i)
	{
		str[i] = str[i + 1];
	}
}

void wordCheck(char* str, char* token) {
	int i = 0;
	const char s[2] = " ";
	bool flag = false;
	int pos = 0;
	int l = -1;
	token = strtok(str, s);

	while (token != NULL)
	{
		for (int j = 0; j < strlen(token); j++)
		{
			if (isalpha(token[j]) == 0 && token[j] != '\0')
			{
				flag = true;
				token = strtok(NULL, s);
				break;
			}
			else flag = false;
		}
		if (flag == false)
		{
			for (i = pos; i < strlen(token) + pos; i++)
			{
				l++;
				if (l < strlen(token))
				{
					str[i] = token[l];
				}
				else break;
			}
			str[i] = ' ';
			pos = i + 1;
			l = -1;
			token = strtok(NULL, s);
			flag = false;
		}
	}

}