#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <windows.h>

#define COUNT 10

struct nodeStruct
{
	int data;
	struct nodeStruct* rightChild;
	struct nodeStruct* leftChild;
};
typedef struct nodeStruct node;

node* insert(int key, node* root);
int _print_t(node* root, int is_left, int offset, int depth, char s[20][255]);
void print_t(node* root);
int countBranches(node* root);
int showMenu();
void count(node* root);

void main() {
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	node* root = NULL;
	int amount = 0;
	int action;
	int key;

	while (1)
	{	
		action = showMenu();
		
		switch (action)
		{
		case 1: {
			printf("Enter number to create new tree node:\n");
			scanf("%d", &key);
			root = insert(key, root);
			break;
		}
		case 2: {
			printf("Enter amount of randomly generated nodes:\n");
			scanf("%d", &amount);
			srand(time(NULL));
			for (int i = 0; i < amount; i++)
			{
				key = rand() % 101 - 50;
				root = insert(key, root);
			}
			break;
		}
		case 3: {
			print_t(root);
			break;
		}
		case 4: {
			if (root == NULL) {
				printf("Tree is empty. Nothing to delete\n");
				break;
			}
			root = NULL;
			break;
		}
		case 5: {
			if (root == NULL) {                     
				printf("Tree is empty\n");
				break;
			}
			printf("Branches on the longest way: %d\n", countBranches(root)-1);
			count(root);   
			break;
		}
		case 6: {
			root = NULL;
			exit(0);
		}
		default:
			break;
		}
	}
}

void count(node* root) {

	if (root == NULL) {
		return 0;
	}
	if ((root->leftChild == NULL && root->rightChild != NULL) ||
		(root->leftChild != NULL && root->rightChild == NULL))
	{
		if (root->leftChild == NULL) {
			printf("%d, ", root->data);
		}else if (root->rightChild == NULL) {
			printf("%d, ", root->data);
		}
	}

	count(root->leftChild);
	count(root->rightChild);

	
}

int showMenu() {
	int action;
	printf("1. Add new node\n");
	printf("2. Randomize tree\n");
	printf("3. Show tree\n");
	printf("4. Delete tree\n");
	printf("5. Task\n");
	printf("6. Exit\n");
	scanf("%d", &action);
	return action;
}

node* insert(int key, node* root) {
	if (root == NULL)
	{
		root = (node*)malloc(sizeof(node));
		root->data = key;
		root->leftChild = NULL;
		root->rightChild = NULL;
	}
	else if (key < root->data)
	{
		root->leftChild = insert(key, root->leftChild);
	}
	else
	{
		root->rightChild = insert(key, root->rightChild);
	}
	return root;
}

int _print_t(node* root, int is_left, int offset, int depth, char s[20][255]) {
	char b[20];
	int width = 5;

	if (!root) return 0;

	sprintf(b, "(%03d)", root->data);

	int left = _print_t(root->leftChild, 1, offset, depth + 1, s);
	int right = _print_t(root->rightChild, 0, offset + left + width, depth + 1, s);

	for (int i = 0; i < width; i++)
		s[2 * depth][offset + left + i] = b[i];

	if (depth && is_left) {

		for (int i = 0; i < width + right; i++)
			s[2 * depth - 1][offset + left + width / 2 + i] = '-';

		s[2 * depth - 1][offset + left + width / 2] = '+';
		s[2 * depth - 1][offset + left + width + right + width / 2] = '+';

	}
	else if (depth && !is_left) {

		for (int i = 0; i < left + width; i++)
			s[2 * depth - 1][offset - width / 2 + i] = '-';

		s[2 * depth - 1][offset + left + width / 2] = '+';
		s[2 * depth - 1][offset - width / 2 - 1] = '+';
	}
	return left + width + right;
}

void print_t(node* root) {
	if (root == NULL)
	{
		printf("Tree is empty. Nothing to display\n");
		return;
	}

	char s[20][255];
	for (int i = 0; i < 20; i++)
	{
		sprintf(s[i], "%80s", " ");
	}
	_print_t(root, 0, 0, 0, s);
	for (int i = 0; i < 20; i++)
	{
		printf("%s\n", s[i]);
	}
}

int countBranches(node* root) {
	if (root == NULL) {                    
		return 0;
	}

	int leftDepth = countBranches(root->leftChild);
	int rightDepth = countBranches(root->rightChild);

	return max(leftDepth, rightDepth)+1;
}
