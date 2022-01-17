//N7
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <windows.h>

#define CRT_SECURE_NO_WARNINGS

struct node
{
	int data;
	int max;
	struct node* next;
};

typedef struct node node;

node* Push(node* head, int data) {
	node* tmp;

	tmp = (node*)malloc(sizeof(node));

	tmp->data = data;

	if (head == NULL) {
		tmp->max = data;
		tmp->next = NULL;
		return tmp;
	}
	else if (data > head->max) {
		tmp->max = data;
	}
	else
	{
		tmp->max = head->max;
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

int CountToMin(node* head) {
	int counter = 0;
	int position = 1;
	while (head != NULL)
	{
		if (head->data == head->max) {
			printf("max: %d\n", head->data);
			counter++;
			break;
		}
		position++;
		counter++;
		head = head->next;
	}
	printf("Position in stack: %d \n", position);
	return counter;
}

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	node* head = NULL;
	int value = 0, k, c;
	bool flag = true;

	printf("Введите число для помещения в стек:\n");
	scanf("%d", &value);
	head = Push(head, value);

	while (flag == true)
	{
		printf("Menu:\n 1-push;\n 2-view;\n 3-amountToMax;\n 4-pop;\n 5-exit;\n");
		scanf("%d", &k);

		switch (k)
		{
		case 1:
			printf("Enter number:\n");
			scanf("%d", &value);
			head = Push(head, value);
			break;
		case 2:
			printf("Stack content:\n");
			ViewStack(head);
			printf("\n");
			break;
		case 3:
			c = CountToMin(head);
			printf("Elements before max: %d\n", c);
			break;
		case 4:
			head = Pop(head);
			break;
		case 5:
			while (head)
			{
				head = Pop(head);
				printf("Stack content:\n");
				ViewStack(head);
			}
			flag = false;
			break;
		}
	}

}

