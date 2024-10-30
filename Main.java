import java.io.IOException;
import java.lang.Thread;
import java.lang.Integer;
import java.util.*;

public class Main {

	public static final String BLUE_FG = "\u001B[34m";
	public static final String PURPLE_FG = "\u001B[35m";
	public static final String GREEN_BG = "\u001B[42m";
	public static final String YELLOW_BG = "\u001B[43m";
	public static final String COLOR_RESET = "\u001B[0m";

	public static void header() throws IOException, InterruptedException {
		new ProcessBuilder("clear").inheritIO().start().waitFor();
		System.out.println("ML-M3: ACT3 Relocatable Dynamic Partition");
		System.out.println("DELA CRUZ, JOHN LORENZ N.\n");
	}

	public static void pressEnterToContinue() {
		System.out.print("\nPress \'Enter\' key to continue...");
		try {
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (Exception e) {
		}
	}

	public static void jobListDisplay(int jobQuantity, float jobs[], int jobsTatFixed[]) {
		Formatter jobList = new Formatter();
		jobList.format("\n%3s%8s%8s\n", "Jobs", "Size", "TAT");
		for (int i = 0; i < jobQuantity; i++) {
			jobList.format("%3s%10s%6s\n", "J" + (i + 1), jobs[i] + "M", jobsTatFixed[i]);
		}
		System.out.println(jobList);
	}

	public static void newSet(String dataHighlight[][], String oldData[][], String partitionTaken[], float jobs[],
			int partitionQuantity, int currentSet, float availableMemory) {
		Formatter set = new Formatter();
		for (int i = 0; i < partitionQuantity; i++) {
			int indexCounter = 0;
			set.format("%14s", "Partition " + (i + 1) + "|");
			for (int j = (currentSet - 1); j > 0; j--) {
				if (dataHighlight[currentSet - j][i] == "1") {
					set.format("%15s", GREEN_BG + oldData[indexCounter][i] + COLOR_RESET + "|");
				} else {
					set.format("%6s", oldData[indexCounter][i] + "|");
				}
				indexCounter++;
			}
			set.format("%6s", partitionTaken[i] + "|");
			if (partitionTaken[i] != "") {
				set.format("%10s%10s\n", "|", jobs[Integer.valueOf(partitionTaken[i].substring(1)) - 1] + "   |");
			} else if (i == (partitionQuantity - 1)) {
				set.format("%10s%10s\n", "|", availableMemory + "   |");
			} else {
				set.format("%10s%10s\n", "|", "       |");
			}

		}
		System.out.print(set);
	}

	public static void jobsAndTatHighlight(String jobHighlight[], String jobsTatHighlight[], float jobs[],
			int jobsTatFixed[], int jobQuantity) {
		Formatter jobList = new Formatter();
		jobList.format("\n%3s%8s%8s\n", "Jobs", "Size", "TAT");
		for (int i = 0; i < jobQuantity; i++) {
			if (jobHighlight[i] == "1") {
				jobList.format("%12s%10s", YELLOW_BG + "J" + (i + 1) + COLOR_RESET, jobs[i] + "M");
			} else {
				jobList.format("%11s%10s", COLOR_RESET + "J" + (i + 1) + COLOR_RESET, jobs[i] + "M");
			}

			if (jobsTatHighlight[i] == "1") {
				jobList.format("%16s\n", GREEN_BG + " " + jobsTatFixed[i] + " " + COLOR_RESET);
			} else {
				jobList.format("%15s\n", COLOR_RESET + " " + jobsTatFixed[i] + " " + COLOR_RESET);
			}
		}
		System.out.println(jobList);
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		header();

		int jobCounter = 0;
		int jobQuantity = 0;
		float memory = 0;
		float availableMemory = 0;
		float osSize = 0;
		boolean validMemory = false;
		boolean validSize = false;
		boolean validPartition = false;
		boolean validJob = false;
		boolean flag = false;

		System.out.print("Enter memory capacity(M): ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					memory = input.nextFloat();
					if (memory <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.print("Enter memory capacity(M): ");
					}
				} while (memory <= 0);
				validMemory = true;
			} catch (InputMismatchException ex) {
				validMemory = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.print("Enter memory capacity(M): ");
			}
		} while (!validMemory);

		System.out.print("Enter OS Size(M): ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					osSize = input.nextFloat();
					if (osSize <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity(M): " + memory);
						System.out.print("Enter OS Size(M): ");
					} else if (osSize >= memory) {
						System.out.println("Invalid input. The OS size should be less than memory.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity(M): " + memory);
						System.out.print("Enter OS Size(M): ");
					} else {
						validSize = true;
					}
				} while (memory <= 0);
			} catch (InputMismatchException ex) {
				validSize = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.println("Enter memory capacity(M): " + memory);
				System.out.print("Enter OS Size(M): ");
			}
		} while (!validSize);

		availableMemory = memory;
		availableMemory -= osSize;

		// NO of jobs

		System.out.print("No. of Jobs: ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					jobQuantity = input.nextInt();
					if (jobQuantity <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity(M): " + memory);
						System.out.println("Enter OS Size(M): " + osSize);
						System.out.print("No. of Jobs: ");
					}
				} while (jobQuantity <= 0);
				validJob = true;
			} catch (InputMismatchException ex) {
				validPartition = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.println("Enter memory capacity(M): " + memory);
				System.out.println("Enter OS Size(M): " + osSize);
				System.out.print("No. of Jobs: ");
			}
		} while (!validJob);

		float jobs[] = new float[jobQuantity];
		int jobsTat[] = new int[jobQuantity];

		// JOBS sizes

		do {
			int turnAroundTime = 0;
			float number = 0;
			flag = false;
			header();
			System.out.println("Enter memory capacity(M): " + memory);
			System.out.println("Enter OS Size(M): " + osSize);
			System.out.println("No. of Jobs: " + jobQuantity);
			System.out.print("\nInput Job Size (" + (jobCounter + 1) + "/" + jobQuantity + "): ");
			do {
				try {
					Scanner input = new Scanner(System.in);
					number = input.nextFloat();
					if (number > 0) {
						flag = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity(M): " + memory);
						System.out.println("Enter OS Size(M): " + osSize);
						System.out.println("No. of Jobs: " + jobQuantity);
						System.out.print("\nInput Job Size (" + (jobCounter + 1) + "/" + jobQuantity + "): ");
					}
				} catch (InputMismatchException ex) {
					flag = false;
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter memory capacity(M): " + memory);
					System.out.println("Enter OS Size(M): " + osSize);
					System.out.println("No. of Jobs: " + jobQuantity);
					System.out.print("\nInput Job Size (" + (jobCounter + 1) + "/" + jobQuantity + "): ");
				}

			} while (!flag);

			flag = false;
			System.out.print("Input Job " + (jobCounter + 1) + " TAT: ");

			do {
				try {
					Scanner input = new Scanner(System.in);
					turnAroundTime = input.nextInt();
					if (turnAroundTime > 0) {
						flag = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity(M): " + memory);
						System.out.println("Enter OS Size(M): " + osSize);
						System.out.println("No. of Jobs: " + jobQuantity);
						System.out
								.println("\nInput Job Size (" + (jobCounter + 1) + "/" + jobQuantity + "): " + number);
						System.out.print("Input Job " + (jobCounter + 1) + " TAT: ");

					}
				} catch (InputMismatchException ex) {
					flag = false;
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter memory capacity(M): " + memory);
					System.out.println("Enter OS Size(M): " + osSize);
					System.out.println("No. of Jobs: " + jobQuantity);
					System.out.println("\nInput Job Size (" + (jobCounter + 1) + "/" + jobQuantity + "): " + number);
					System.out.print("Input Job " + (jobCounter + 1) + " TAT: ");

				}
			} while (!flag);

			jobs[jobCounter] = number;
			jobsTat[jobCounter] = turnAroundTime;
			jobCounter++;
		} while (jobCounter < jobQuantity);

		int jobsTatFixed[] = jobsTat.clone();
		flag = false;
		// job list
		header();
		System.out.println("Enter memory capacity(M): " + memory);
		System.out.println("Enter OS Size(M): " + osSize);
		System.out.println("No. of Jobs: " + jobQuantity);
		jobListDisplay(jobQuantity, jobs, jobsTatFixed);

		int partitionQuantity = 0;
		int currentSet = 0;
		int previousRank = 0;
		int rank = 0;
		int totalTat = 0;

		int jobAllocated[] = new int[jobQuantity];
		String failedJobs[] = new String[jobQuantity];
		int totalAllocations = 0;

    String jobHighlight[] = new String[jobQuantity];
		String temporaryAllocation[] = new String[jobQuantity * 2];

		for (int i = 0; i < jobQuantity; i++) {
			if (jobAllocated[i] == 0) {
				availableMemory -= jobs[i];
				if (availableMemory >= 0) {
					jobAllocated[i] = 1;
					temporaryAllocation[rank] = ("J" + String.valueOf(i + 1));
          jobHighlight[i] = "1";
					partitionQuantity++;
					rank++;
				} else {
					availableMemory += jobs[i];
				}
			}
		}

		if (availableMemory > 0) {
			partitionQuantity++;
		}

		String partitionTaken[] = new String[partitionQuantity];

		for (int i = 0; i < partitionQuantity; i++) {
			partitionTaken[i] = "";
			if (temporaryAllocation[i] != null) {
				partitionTaken[i] = temporaryAllocation[i];
			}
		}

		for (int i = 0; i < jobQuantity; i++) {
			totalTat += jobsTat[i];
		}
    
		String jobsTatHighlight[] = new String[jobQuantity];
		String oldData[][] = new String[totalTat][partitionQuantity];
		String dataHighlight[][] = new String[totalTat][partitionQuantity];

		pressEnterToContinue();

		boolean maxSet = false;
		boolean validLoop = true;

		int oldIndex = 0;
		do {
			boolean validSet = false;
			boolean indexChanged = false;
			rank = 0;

			if (flag) {
				for (int i = 0; i < partitionQuantity; i++) {
					if (partitionTaken[i] != "") {
						partitionTaken[rank] = partitionTaken[i];
						validSet = true;
						rank++;
					}
				}
				if (rank != oldIndex) {
					indexChanged = true;
				}

				for (int i = 0; i < jobQuantity; i++) {
					if (jobAllocated[i] == 0) {
						availableMemory -= jobs[i];
						if (availableMemory >= 0) {
							jobAllocated[i] = 1;
							partitionTaken[rank] = ("J" + String.valueOf(i + 1));
              jobHighlight[i] = "1";
							validSet = true;
							validLoop = true;
							rank++;
						} else {
							availableMemory += jobs[i];
						}
					}
				}
				oldIndex = rank;
				for (int i = 0; i < partitionQuantity; i++) {
					if (dataHighlight[currentSet][i] == "0") {
						previousRank = i;
					}
				}

        String tempJobHolder = partitionTaken[previousRank];
        boolean isRemoved = false;
        
				if (indexChanged && partitionTaken[previousRank] == oldData[currentSet - 1][previousRank]) {
          partitionTaken[previousRank] = "";
          isRemoved = true;
				}

        for (int i = 0; i < partitionQuantity; i++){
          if(partitionTaken[i] != tempJobHolder) {
            isRemoved = true;
          } else {
            isRemoved = false;
            break;
          }
        }

        if (isRemoved){
          partitionTaken[previousRank] = tempJobHolder;
        }
        
			} else {
				flag = true;
				validSet = true;
			}

      header();
  		System.out.println("Enter memory capacity(M): " + memory);
  		System.out.println("Enter OS Size(M): " + osSize);
  		System.out.println("No. of Jobs: " + jobQuantity);
  		jobsAndTatHighlight(jobHighlight, jobsTatHighlight, jobs, jobsTatFixed, jobQuantity);

			for (int counter = 0; counter < partitionQuantity; counter++) {
				oldData[currentSet][counter] = partitionTaken[counter];
			}

			currentSet++;
			Formatter set = new Formatter();

			if (currentSet == 1) {
				set.format("\n%20s%20s\n", "|SET 1|", "| MEMORY  |");
				set.format("%14s%6s%10s%10s\n", "OS Partition|", "OS|", "|", osSize + "   |");
				for (int i = 0; i < partitionQuantity; i++) {
					if (partitionTaken[i] != "") {
						set.format("%14s%10s%10s%10s\n", "Partition " + (i + 1) + "|",
								partitionTaken[i] + COLOR_RESET + "|", "|",
								jobs[Integer.valueOf(partitionTaken[i].substring(1)) - 1] + "   |");
					} else if (i == (partitionQuantity - 1)) {
						set.format("%14s%10s%10s%10s\n", "Partition " + (i + 1) + "|",
								partitionTaken[i] + COLOR_RESET + "|", "|", availableMemory + "   |");
					} else {
						set.format("%14s%10s%10s%10s\n", "Partition " + (i + 1) + "|",
								partitionTaken[i] + COLOR_RESET + "|", "|", "      |");
					}
				}
				System.out.println(set);
				pressEnterToContinue();
			}

			else if (currentSet == 2) {
				set.format("\n%20s%1s%20s\n", "|SET 1|", "SET 2|", "| MEMORY  |");
				set.format("%14s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 3) {
				set.format("\n%20s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 4) {
				set.format("\n%20s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|", "|",
						osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 5) {
				set.format("\n%20s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|",
						"| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|", "OS|", "|",
						osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 6) {
				set.format("%20s%1s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|", "SET 6|",
						"| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|", "OS|",
						"OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 7) {
				set.format("%20s%1s%1s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|", "SET 6|",
						"SET 7|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|", "OS|",
						"OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 8) {
				set.format("%20s%1s%1s%1s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|",
						"SET 6|", "SET 7|", "SET 8|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|", "OS|",
						"OS|", "OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 9) {
				set.format("%20s%1s%1s%1s%1s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|",
						"SET 6|", "SET 7|", "SET 8|", "SET 9|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|",
						"OS|", "OS|", "OS|", "OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 10) {
				set.format("%20s%1s%1s%1s%1s%1s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|",
						"SET 6|", "SET 7|", "SET 8|", "SET 9|", "SET 10|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|", "OS|",
						"OS|", "OS|", "OS|", "OS|", "OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			else if (currentSet == 11) {
				set.format("%20s%1s%1s%1s%1s%1s%1s%1s%1s%1s%20s\n", "|SET 1|", "SET 2|", "SET 3|", "SET 4|", "SET 5|",
						"SET 6|", "SET 7|", "SET 8|", "SET 9|", "SET 10|", "SET 11|", "| MEMORY  |");
				set.format("%14s%6s%6s%6s%6s%6s%6s%6s%6s%6s%6s%6s%10s%10s\n", "OS Partition|", "OS|", "OS|", "OS|",
						"OS|", "OS|", "OS|", "OS|", "OS|", "OS|", "OS|", "OS|", "|", osSize + "   |");
				System.out.print(set);
				newSet(dataHighlight, oldData, partitionTaken, jobs, partitionQuantity, currentSet, availableMemory);
				pressEnterToContinue();
			}

			if (!validLoop) {
				maxSet = true;
			}
			validLoop = false;

			for (int i = 0; i < partitionQuantity; i++) {
				if (partitionTaken[i] != "") {
					int index = Integer.valueOf(partitionTaken[i].substring(1)) - 1;
					if (jobAllocated[index] == 1) {
						if (jobsTat[index] == 1) {
							partitionTaken[i] = "";
							dataHighlight[currentSet][i] = "1";
							jobsTatHighlight[index] = "1";
							availableMemory += jobs[index];
							jobsTat[index]--;
						} else if (jobsTat[index] > 1) {
							dataHighlight[currentSet][i] = "0";
							jobsTat[index]--;
						}
					}
				}
			}

			if (validSet) {
				totalAllocations++;
			}

			if (!maxSet) {
				header();
				System.out.println("Enter memory capacity(M): " + memory);
				System.out.println("Enter OS Size(M): " + osSize);
				System.out.println("No. of Jobs: " + jobQuantity);
				jobsAndTatHighlight(jobHighlight, jobsTatHighlight, jobs, jobsTatFixed, jobQuantity);
			}

			for (int i = 0; i < partitionQuantity; i++) {
				if (partitionTaken[i] != "") {
					validLoop = true;
					break;
				}
			}

		} while (!maxSet);

		int failedQuantity = 0;

		for (int i = 0; i < jobQuantity; i++) {
			if (jobAllocated[i] == 0) {
				failedJobs[failedQuantity] = "J" + String.valueOf(i + 1);
				failedQuantity++;
			}
		}

		if (failedQuantity == 0) {
			System.out.println("\nConclusion:\n\tAll jobs were executed.\n\tThere are total of " + totalAllocations
					+ " set allocations.");
		} else {
			System.out.print("\nConclusion:\n\tNot all jobs were exceuted. ");
			for (int i = 0; i < failedQuantity; i++) {
				if (i == 0 && failedQuantity != 2) {
					System.out.print(failedJobs[i]);
				} else if (failedQuantity == 2) {
					System.out.print(failedJobs[i] + " and " + failedJobs[i + 1]);
					break;
				} else if (i != 0 && i == (failedQuantity - 1)) {
					System.out.print(" and " + failedJobs[i]);
					break;
				} else {
					System.out.print(", " + failedJobs[i]);
				}

			}
			System.out.println(" failed to load.");
			if (totalAllocations > 0) {
				System.out.println("\tThere are only " + totalAllocations + " sets of allocations.");
			} else {
				System.out.println("\tThere are no sets of allocations.");
			}
		}
	}
}