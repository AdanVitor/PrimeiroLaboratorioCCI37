package util;

import java.util.ArrayList;
import java.util.Random;

import entities.*;


public class Statistics {

	private static Random rand = new Random();

	// simulation parameters
	static double arriveClientMean = 5, arriveCallMean = 10,
			callDurationMin = 1, callDurationMax = 10,
			maximumCallWaitTime = 10, eletronicCashServiceTimeMean = 4,
			eletronicCashServiceTimeStd_dev = 2, cashServiceTimeMean = 7,
			cashServiceTimeStd_dev = 3, manageServiceTimeMean = 10,
			manageServiceTimeStd_dev = 4;

	// Normalized Random Number (0 to 1.0)
	public static double random() {
		return rand.nextDouble();
	}

	// Normal generates a normalized normal random number
	// using the polar method N(0,1) (avg = 0 std_dev=1)
	public static double normal() {
		double Y, W, V1, V2;
		do {
			V1 = 2 * random() - 1;
			V2 = 2 * random() - 1;
			W = V1 * V1 + V2 * V2;
			if (W < 1.0) {
				Y = Math.sqrt(-2 * Math.log(W) / W);
				V1 *= Y;
				V2 *= Y;
			}
		} while (W > 1.0);
		double U = V1;
		return U;
	}

	// Normal generates normal distribution variates with average avg
	// and standard deviation std_dev
	public static double normal(double avg, double std_dev) {
		double U = avg + std_dev * normal();
		return U;
	}

	static double lim_inf = 0.2;
	static double lim_sup;

	// Normal generates a normal dist number cuttted by lim_inf and lim_sup
	public static double normalLimited(double avg, double std_dev) {
		int n = 0;
		double U;
		lim_sup = 2 * std_dev + avg; // limiting the value of time in two times
										// the standard deviation plus the avg
		do {
			n++;
			U = normal(avg, std_dev);
			if (n > 100) // try 100 times
				break;
		} while (U < lim_inf || U > lim_sup);
		if (n < 100)
			return U;
		if (U < lim_inf)
			U = lim_inf;
		else
			U = lim_sup;
		return U;
	}

	static double TINY = Math.pow(10, -38);

	// Exponential generates exponiential distribution variates
	public static double exponential(double beta) {
		double U = random();
		if (U < TINY)
			U = TINY;
		U = -beta * Math.log(U);
		return U;
	}

	// Uniform generates uniform distribution variates between alpha and beta
	public static double uniform(double alpha, double beta) {
		double U = random();
		if (alpha > 0)
			U = alpha + (beta - alpha) * U;
		return U;
	}

	public static double getNextClientArrival(double initialTime) {
		return initialTime + exponential(arriveClientMean);
	}

	public static double getCashServiceTime() {
		return normalLimited(cashServiceTimeMean, cashServiceTimeStd_dev);
	}

	public static double getEletronicCashServiceTime() {
		return normalLimited(eletronicCashServiceTimeMean,
				eletronicCashServiceTimeStd_dev);
	}

	public static double getManageServiceTime() {
		return normalLimited(manageServiceTimeMean, manageServiceTimeStd_dev);
	}

	public static double getNextCallArrival(double initialTime) {
		return initialTime + exponential(arriveCallMean);
	}

	public static double getCallDuration() {
		return uniform(callDurationMin, callDurationMax);
	}
	
	
	// Statistic log
	public static ArrayList<Entity> callAndClientsAttended = new ArrayList<Entity>();
	public static ArrayList<Call> callsNotAttended = new ArrayList<Call>();
	
	public static ArrayList<Double> clientServiceTime = new ArrayList<Double>();
	public static ArrayList<Double> clientWait = new ArrayList<Double>();
	public static ArrayList<Double> clientTotalTime = new ArrayList<Double>();

	public static double waitTime = 0;
	public static int clientsNumber = 0;

	public static void addInClientStatistics(Client client) {
		clientServiceTime.add(client.endTime - client.startTime);
		clientWait.add(client.startTime - client.arrivalTime);
	    clientTotalTime.add(client.endTime - client.arrivalTime);
		
	}

}
