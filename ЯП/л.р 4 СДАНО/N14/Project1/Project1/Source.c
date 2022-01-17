//�� ��� ���������� � ���������� �������� ����� �������� ��������,
//����� ��������� � �����. ������� ��� ��������� �������� �����, ������� ��� ������� �������� �� ���������. 

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
		printf("������������ �%d\n", i);
		printf("  �����: %s\n", info[i].number);
		printf("  ����� ���������: %d:%d:%d\n", info[i].hours, info[i].mins, info[i].secs);
		printf("  ���� ������ �� �������: %f\n", info[i].costPerSec);
	}
}

int addNewSub(struct infoList info[], int subsAmount) {
	printf("�����: ");
	scanf("%s", info[subsAmount].number);
	printf("����: ");
	scanf("%d", &info[subsAmount].hours);
	printf("������: ");
	//while (getchar() != '\n');
	scanf(" %d", &info[subsAmount].mins);
	printf("�������: ");
	//while (getchar() != '\n');
	scanf("%d", &info[subsAmount].secs);
	printf("���� ������ �� �������: ");
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
	printf("����������� ������:\n");
	if (subsAmount == 0) {
		printf("������ �������\n");
		return;
	}
	showAllSubs(info, subsAmount);
	printf("\n");
	return subsAmount;
}

void changeSub(struct infoList info[], int subToChange) {
	printf("�����: ");
	scanf("%s", info[subToChange].number);
	//gets(info[subToChange].number);
	printf("����: ");
	scanf("%d", &info[subToChange].hours);
	printf("������: ");
	//while (getchar() != '\n');
	scanf(" %d", &info[subToChange].mins);
	printf("�������: ");
	//while (getchar() != '\n');
	scanf("%d", &info[subToChange].secs);
	printf("���� ������ �� �������: ");
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
			printf("�� ������\n");
		}
		else if (pos != NULL)
		{
			printf("�������� �������:\n");
			printf("  �����: %s\n", info[i].number);
			printf("  ����� ���������: %d:%d:%d\n", info[i].hours, info[i].mins, info[i].secs);
			printf("  ���� ������ �� �������: %f\n", info[i].costPerSec);
			printf("  ����� � ������: %f\n", info[i].cost);
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

	printf("������� ���������� �� ��������:\n");
	subsAmount = addNewSub(info, subsAmount);
	showAllSubs(info, subsAmount);
	
	while (1)
	{
		printf("\n�������� ��������:\n");
		printf("  1. �������� ������ ��������\n");
		printf("  2. ������� ��������\n");
		printf("  3. �������� ��������\n");
		printf("  4. �������� ���� ������\n");
		printf("  5. �����\n");
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
				printf("�������� ���������� ����� �������� �� ������ ����� �������:\n");
				showAllSubs(info, subsAmount);
				scanf("%d", &subToDelete);
				subsAmount = deleteSub(info, subsAmount, subToDelete);
				break;
			}
			case 3: {
				printf("�������� ���������� ����� �������� �� ������, ������� �� ������ ��������:\n");
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
				printf("������� ����� ��� ����� ������:\n");
				char numToFind[13];
				scanf("%s", &numToFind);
				while (getchar() != '\n');
				findUser(info, numToFind);
			}
			default:
				printf("�������� �������!\n");
				break;
		}
	}
}
