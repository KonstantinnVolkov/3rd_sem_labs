//На АТС информация о разговорах содержит номер телефона абонента,
//время разговора и тариф. Вывести для заданного абонента сумму, которую ему следует оплатить за разговоры. 

#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <locale.h>
#include <stdlib.h>
#include <windows.h>
#include <stdbool.h>

struct infoList
{
	char number[13];
	int hours;
	int mins;
	int secs;
	float costPerSec;
	float cost;
}info[10];

void showAllSubs(struct infoList info[], int subsAmount) {
	for (int i = 0; i < subsAmount; i++)
	{
		printf("Пользователь №%d\n", i);
		printf("  Номер: %s\n", info[i].number);
		printf("  Время разговора: %d:%d:%d\n", info[i].hours, info[i].mins, info[i].secs);
		printf("  Цена тарифа за секунду: %f\n", info[i].costPerSec);
	}
}

int addNewSub(struct infoList info[], int subsAmount) {
	printf("Номер: ");
	scanf("%s", info[subsAmount].number);
	printf("Часы: ");
	scanf("%d", &info[subsAmount].hours);
	printf("Ммнуты: ");
	//while (getchar() != '\n');
	scanf(" %d", &info[subsAmount].mins);
	printf("Секунды: ");
	//while (getchar() != '\n');
	scanf("%d", &info[subsAmount].secs);
	printf("Цена тарифа за секунду: ");
	while (getchar() != '\n');
	scanf("%f", &info[subsAmount].costPerSec);
	printf("\n");
	info[subsAmount].cost = (info[subsAmount].hours * 3600 + info[subsAmount].mins * 60 + info[subsAmount].secs) * info[subsAmount].costPerSec;

	return ++subsAmount;
}

int deleteSub(struct infoList info[], int subsAmount, int subToDelete) {
	for (int i = subToDelete; i < subsAmount - 1; i++)
	{
		info[i] = info[i + 1];
	}
	subsAmount--;
	printf("Обновленный список:\n");
	if (subsAmount == 0) {
		printf("Нечего удалять\n");
		return;
	}
	showAllSubs(info, subsAmount);
	printf("\n");
	return subsAmount;
}

void changeSub(struct infoList info[], int subToChange) {
	printf("Номер: ");
	scanf("%s", info[subToChange].number);
	//gets(info[subToChange].number);
	printf("Часы: ");
	scanf("%d", &info[subToChange].hours);
	printf("Ммнуты: ");
	//while (getchar() != '\n');
	scanf(" %d", &info[subToChange].mins);
	printf("Секунды: ");
	//while (getchar() != '\n');
	scanf("%d", &info[subToChange].secs);
	printf("Цена тарифа за секунду: ");
	while (getchar() != '\n');
	scanf("%f", &info[subToChange].costPerSec);
	info[subToChange].cost = (info[subToChange].hours * 3600 + info[subToChange].mins * 60 + info[subToChange].secs) * info[subToChange].costPerSec;
	printf("\n");
}

void findUser(struct infoList info[], char* numToFind){
	int pos;
	bool flag = false;
	for (int i = 0; i < 10; i++)
	{
		pos = strstr(info[i].number, numToFind);
		if (!flag && pos == NULL && i==9)
		{
			printf("Не найден\n");
		}
		else if (pos != NULL)
		{
			printf("Найденый абонент:\n");
			printf("  Номер: %s\n", info[i].number);
			printf("  Время разговора: %d:%d:%d\n", info[i].hours, info[i].mins, info[i].secs);
			printf("  Цена тарифа за секунду: %f\n", info[i].costPerSec);
			printf("  Сумма к оплате: %f\n", info[i].cost);
			flag = true;
		}
	}
}


void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	int subToDelete;
	int subToChange;
	int subsAmount = 0;	
	int action;

	printf("Введите информацию об абоненте:\n");
	subsAmount = addNewSub(info, subsAmount);
	showAllSubs(info, subsAmount);
	
	while (1)
	{
		printf("\nВыберите действие:\n");
		printf("  1. Добавить нового абонента\n");
		printf("  2. Удалить абонента\n");
		printf("  3. Заменить абонента\n");
		printf("  4. Показать весь список\n");
		printf("  5. Поиск\n");
		while (getchar() != '\n');
		scanf("%d", &action);
		printf("\n");

		switch (action)
		{
			case 1: {
				while (getchar() != '\n');
				subsAmount = addNewSub(info, subsAmount);
				break;
			}
			case 2: {
				printf("Выберите ПОРЯДКОВЫЙ номер абонента из списке чтобы удалить:\n");
				showAllSubs(info, subsAmount);
				scanf("%d", &subToDelete);
				subsAmount = deleteSub(info, subsAmount, subToDelete);
				break;
			}
			case 3: {
				printf("Выберите ПОРЯДКОВЫЙ номер абонента из списка, который вы хотите изменить:\n");
				showAllSubs(info, subsAmount);
				scanf("%d", &subToChange);
				changeSub(info, subToChange);
				break;
			}
			case 4: {
				showAllSubs(info, subsAmount);
				break;
			}
			case 5: {
				printf("Введите номер или часть номера:\n");
				char numToFind[13];
				scanf("%s", &numToFind);
				while (getchar() != '\n');
				findUser(info, numToFind);
			}
			default:
				printf("Неверная команда!\n");
				break;
		}
	}
}
