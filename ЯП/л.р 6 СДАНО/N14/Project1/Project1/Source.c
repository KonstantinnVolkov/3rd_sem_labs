//14. Создать новый стек, в который поместить каждый третий элемент первого стека. 
#define CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <windows.h>

struct node
{
	int data;
	struct node* next;
};
typedef struct node node;

node* Push(node* head, int data);
node* Pop(node* head);
void ViewStack(node* head);
int showMenu();
node* task(node* head);

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	node* head = NULL;
	int action, value;

	printf("Enter element of stack: ");
	scanf("%d", &value);
	head = Push(head, value);

	while (true)
	{
		action = showMenu();

		switch (action)
		{
		case 1:                          //push el to stack
			printf("Enter number:\n");
			scanf("%d", &value);
			head = Push(head, value);
			break;
		case 2:							//print stack

			if (head == NULL)
			{
				printf("Main stack is empty!\n");
			}
			else
			{
				printf("Stack content:\n");
				ViewStack(head);
				printf("\n");
			}
			break;
		case 3:							//here's the task
			if (head == NULL)		//если главный стек пустой, то задание не выполняется
				printf("Main stack is empty!\n");
			else
			{
				node* newHead = NULL;			//создаем  голову новому стеку
				newHead = task(head, newHead);	
				if (newHead == NULL)			//если в главном стеке меньше трех элементов, то новый стек будет пуст.
												//выводим соответствующее сообщение
					printf("\nNot enough elements in main stack! \nNew stack is empty!\n\n");
				else 
					ViewStack(newHead);			//вывод содержимого нового стека
			}
			break;
		case 4:							//deleting element
			if (head == NULL)
			{
				printf("Nothing to delete!\n");
			}
			else
			{
				head = Pop(head);
			}
			break;
		case 5:							//finish programm
			exit(0);
			break;
		}
	}
}

node* task(node* head, node* newHead) {

	int counter = 1;

	while (head != NULL)        //цикл по главному стеку
	{
		if (counter == 3)
		{
			newHead = Push(newHead, head->data);		//если мы на 3 эл-те главного стека, то пушим его в новый
			counter = 0;
		}
		head = head->next;		//переход на некст эл-т главного стека
		counter++;
	}
	return newHead;
}

int showMenu() {
	printf("Menu:\n");
	printf("  1: Push\n");
	printf("  2: Viev\n");
	printf("  3: Task\n");
	printf("  4: Pop\n");
	printf("  5: Exit\n");
	printf("Choose option: ");
	int action;
	scanf("%d", &action);

	return action;
}

#pragma region StackFunctions

node* Push(node* head, int data) {
	node* tmp;

	tmp = (node*)malloc(sizeof(node));

	tmp->data = data;

	if (head == NULL) {
		tmp->next = NULL;
		return tmp;
	}

	tmp->next = head;
	return tmp;
}

node* Pop(node* head) {
	if (head == NULL) {
		return NULL;
	}
	node* tmp = head->next;
	free(head);
	return tmp;
}

void ViewStack(node* head) {
	while (head != NULL)
	{
		printf("%d ", head->data);
		head = head->next;
	}
	printf("\n");
}

#pragma endregion


