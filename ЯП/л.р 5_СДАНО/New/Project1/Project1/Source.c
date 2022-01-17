#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
#include <windows.h>
#include <stdbool.h>

#define _CRT_SECURE_NO_WARNINGS

typedef struct studentInfo StudentInfo;
struct studentInfo
{
	char name[10];
	int mark;
};
StudentInfo students[10];

int showMenu();

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	FILE* DBFile = NULL;
	FILE* lessThanFiveFile = NULL;
	char DBFilePath[50] = "E:\\Лабы\\ЯП\\л.р 5_СДАНО\\N7\\Files\\Database.txt";
	char lessThanFiveFilePath[50] = "E:\\Лабы\\ЯП\\л.р 5_СДАНО\\N7\\Files\\LessThanFive.txt";
	int action;

	while (true) {
		action = showMenu();
		int i = 0;
		int counter = 0;

		switch (action) {
		case 1: {
			int counter = 0;

			DBFile = fopen(DBFilePath, "rt");
			lessThanFiveFile = fopen(lessThanFiveFilePath, "rt");
			while (fscanf(DBFile, "%s %d", &students[i].name, &students[i].mark) != EOF)
			{
				i++;
			}
			if (i == 0)
			{
				printf("Файл пуст\n");
				continue;
			}
			fclose(DBFile);
			fclose(lessThanFiveFile);
			DBFile = fopen(DBFilePath, "w+t");
			lessThanFiveFile = fopen(lessThanFiveFilePath, "w+t");
			for (int j = 0; j < i; j++)
			{
				if (students[j].mark < 5)
				{
					fprintf(lessThanFiveFile, "%s ", students[j].name);
					fprintf(lessThanFiveFile, "%d\n", students[j].mark);
					counter++;
				}
				else
				{
					fprintf(DBFile, "%s ", students[j].name);
					fprintf(DBFile, "%d\n", students[j].mark);
				}
			}
			fprintf(lessThanFiveFile, "Ниже 5: %d\n", counter);
			fclose(DBFile);
			fclose(lessThanFiveFile);
			break;
		}
		case 2: {
			exit(0);
		}
		}
	}
}

int showMenu() {
	int action;
	printf("1. Применить изменения файла\n");
	printf("2. Завершение работы\n");
	scanf("%d", &action);
	return action;
}