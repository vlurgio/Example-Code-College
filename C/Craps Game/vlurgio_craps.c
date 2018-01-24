
/*

Author: Vinny Lurgio
Date: 3/12/2016
Class: CS 222
This is a program that allows the user to play a craps game. It also
creates a save file so you can play your game until you run out of money 
or you don't have enough for the minimum bet. 
*/
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#define DEFAULTBILLVAL 100.00
int main()
{
	void beginning(int[], float*);
	int rolling(int*, int*);
	void ending(float, int[]);
	void playing(float*, int[], int*, int*);

	float currentBank = DEFAULTBILLVAL;
	int status[4] = { 0,0,0,0 };
	int d1;
	int d2;

	beginning(status, &currentBank);
	playing(&currentBank, status, &d1, &d2);
	ending(currentBank, status);


}

void beginning(int status[], float *bank)
{
	FILE *save = fopen("save.txt", "r+");
	int stats[4];
	float nbank;
	if (save != NULL)

	{
		int i;
		fscanf(save, "%1d%1d%1d%1d%f", &stats[0], &stats[1], &stats[2], &stats[3], &nbank);
		status[0] = stats[0];
		status[1] = stats[1];
		status[2] = stats[2];
		status[3] = stats[3];
		*bank = nbank;
		fclose(save);
	}
}

int rolling(int *d1, int *d2)
{
	int sum;

	srand(time(NULL));

	*d1 = rand() % 6 + 1;
	*d2 = rand() % 6 + 1;

	sum = *d1 + *d2;

	return sum;
}

void ending(float cBank, int status[])
{
	printf("Thank you for playing here are your ending results:\n");
	printf("Current Bank Roll: %.2f\n", cBank);
	printf("# of times won: %d\n", status[0]);
	printf("# of times lost: %d\n", status[1]);
	printf("# of times bet for yourself: %d\n", status[2]);
	printf("# of times bet against yourself: %d\n", status[3]);
	scanf("%c");

	FILE* save = fopen("save.txt", "w");
	int i;
	for (i = 0; i < 4; i++)
		fprintf(save, "%d", status[i]);
	fprintf(save, "%.2f", cBank);
	if (cBank < 5)
	{
		remove("save.txt");
		printf("You have now run out of money the next time the game starts it will start from the beginning.");
	}
}

void playing(float *bank, int status[], int *d1, int *d2)
{
	float currentBet = 0;
	char dummy;
	char forAgainst;
	int sum;
	int quit = 1;
	
	int point = 0;
	printf("Welcome to Vinny's Craps game.\n");
	while (*bank > 5 && quit == 1)
	{
		printf("The minimum bet in this game will be $5.00 and you currently have: %.2f.\n", *bank);
		while (currentBet < 5 || *bank - currentBet < 0)
		{
			printf("Please enter your bet:");
			scanf("%f%c", &currentBet, &dummy);
			if (currentBet < 5)
			{
				printf("I'm sorry that bet is less than the minimum bet. You must bet at least $5.00\n");
			}
			else if (*bank - currentBet < 0)
			{
				printf("I'm sorry you don't have enough money in your bank to make that bet.\n");
				printf("Your wallet's current amount is %.2f\n", *bank);
			}
		}
		forAgainst = 'b';
		while (forAgainst != 'f' && forAgainst != 'a')
		{
			printf("Ok. So your current bet is %.2f. Would like to bet for or against yourself.\n", currentBet);
			printf("Please type f for for and a for against:");
			scanf("%c%c", &forAgainst, &dummy);
		}
		printf("Press any key to roll the dice");
		scanf("%c", &dummy);
		if (forAgainst == 'f')
		{
			status[2]++;
			sum = rolling(d1, d2);
			printf("You rolled %d and %d that gives you a sum of %d.\n", *d1, *d2, sum);
			switch (sum)
			{
			case 7:
				status[0]++;
				printf("You won the bet!");
				*bank += currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 11:
				status[0]++;
				printf("You won the bet!");
				*bank += currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 2:
				status[1]++;
				printf("You lost the bet!");
				*bank -= currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 3:
				status[1]++;
				printf("You lost the bet!");
				*bank -= currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 12:
				status[1]++;
				printf("You lost the bet!");
				*bank -= currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				printf("\n\n");
				currentBet = 0;
				break;
			default:
				point = sum;
				printf("You now will have to continue to roll until you either get %d\n", point);
				printf("again or you roll a 7. If you roll a 7 you will win while if you roll\n");
				printf("the bet again you will lose.\n");
				if (*bank - (currentBet * 2) >= 0)
				{
					printf("Would you like to double your bet? \n\n");
					printf("Type 1 for yes and 0 for no:");
					scanf("%d%c", &quit, &dummy);
					if (quit == 1)
					{
						currentBet = 2 * currentBet;
					}
				}
				sum = 0;
				while (sum != point && sum != 7)
				{
					printf("Press any key to roll");
					scanf("%c", &dummy);
					sum = rolling(d1, d2);
					printf("You rolled %d and %d that gives you a sum of %d\n\n", *d1, *d2, sum);
				}
				if (sum == 7)
				{
					status[1]++;
					printf("You lost the bet!");
					*bank -= currentBet;
					printf("You now have $%.2f money in your bank.\n", *bank);
					printf("Would you like to bet again? Type 0 for no and 1 for yes.\n\n");
					scanf("%d%c", &quit, &dummy);
					currentBet = 0;
				}
				if (sum == point)
				{
					status[0]++;
					printf("You won the bet!");
					*bank += currentBet;
					printf("You now have $%.2f money in your bank.\n", *bank);
					printf("Would you like to bet again? Type 0 for no and 1 for yes.\n\n");
					scanf("%d%c", &quit, &dummy);
					currentBet = 0;
				}

			}

		}
		else if (forAgainst == 'a')
		{
			status[3]++;
			sum = rolling(d1, d2);
			printf("You rolled %d and %d that gives you a sum of %d.\n", *d1, *d2, sum);
			switch (sum)
			{
			case 7:
				status[1]++;
				printf("You lost the bet!");
				*bank -= currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.\n\n");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 11:
				status[1]++;
				printf("You lost the bet!");
				*bank -= currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.\n\n");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 2:
				status[0]++;
				printf("You won the bet!");
				*bank += currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 3:
				status[0]++;
				printf("You won the bet!");
				*bank += currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			case 12:
				status[0]++;
				printf("You won the bet!");
				*bank += currentBet;
				printf("You now have $%.2f money in your bank.\n", *bank);
				printf("Would you like to bet again? Type 0 for no and 1 for yes.");
				scanf("%d%c", &quit, &dummy);
				currentBet = 0;
				printf("\n\n");
				break;
			default:
				point = sum;
				printf("You now will have to continue to roll until you either get %d\n", point);
				printf("again or you roll a 7. If you roll a 7 you will win while if you roll\n");
				printf("the bet again you will lose.\n");
				if (*bank - (currentBet * 2) >= 0)
				{
					printf("Would you like to double your bet ? \n\n");
					printf("Type 1 for yes and 0 for no:");
					scanf("%d%c", &quit, &dummy);
					if (quit == 1)
					{
						currentBet = 2 * currentBet;
					}
				}
				sum = 0;
				while (sum != point && sum != 7)
				{
					printf("Press any key to roll");
					scanf("%c", &dummy);
					sum = rolling(d1, d2);
					printf("You rolled %d and %d that gives you a sum of %d\n\n", *d1, *d2, sum);
				}
				if (sum == 7)
				{
					status[1]++;
					printf("You lost the bet!");
					*bank -= currentBet;
					printf("You now have $%.2f money in your bank.\n", *bank);
					printf("Would you like to bet again? Type 0 for no and 1 for yes.\n\n");
					scanf("%d%c", &quit, &dummy);
					currentBet = 0;
				}
				if (sum == point)
				{
					status[1]++;
					printf("You lost the bet!");
					*bank -= currentBet;
					printf("You now have $%.2f money in your bank.\n", *bank);
					printf("Would you like to bet again? Type 0 for no and 1 for yes.\n\n");
					scanf("%d%c", &quit, &dummy);
					currentBet = 0;
					printf("\n\n");
				}

			}

		}

	}
	return;
}
