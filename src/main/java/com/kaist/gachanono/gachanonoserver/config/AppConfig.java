package com.kaist.gachanono.gachanonoserver.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kaist.gachanono.gachanonoserver"})
public class AppConfig {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

	// private String GetDollarAmount(String amount, int budget)
	// {
	// 	String retval = "";

	// 	// Parse Number in amount string
	// 	int intAmount = 0;
	// 	try
	// 	{
	// 		intAmount = Integer.parseInt(amount.replace("$", "").replace(",", ""));
	// 		retval = String.format("%,d", intAmount);
	// 	}
	// 	catch (Exception e)
	// 	{
	// 		System.out.println("[Parse error] " + e.getMessage());
	// 		return retval;
	// 	}

	// 	// Check amount with own budget
	// 	if(intAmount > budget)
	// 	{
	// 		System.out.println("[Value error] amount cannot bigger than budget");
	// 		return retval;
	// 	}

	// 	// Convert amount to dollar format string(e.g. 1000 -> $1,000)
	// 	retval = String.format("$%,d", intAmount);

	// 	// Return dollar format string
	// 	return retval;
	// }

	// public void PrintDollarAmount(String amount, int budget)
	// {
	// 	System.out.println(GetDollarAmount(amount, budget));
	// }

	// private Float GetApproximatedRoot(String equation, int itrCount) {
	// 	Float root = null;

	// 	// Parse equation. 
	// 	Equation eq = new Equation(equation);

	// 	// Validate equation.
	// 	if(eq.IsValid() == false)
	// 	{
	// 		System.out.println("[Error] Invalid equation");
	// 		return root;
	// 	}

	// 	// Solve equation with iteration while count is more than itrCount. 
	// 	for(int i = 0; i < itrCount; i++)
	// 	{
	// 		root = eq.GetRoot(root);
	// 	}

	// 	// Return approximated root
	// 	return root;
	// }

	// private SchedulingQueue queue;
	// private int Scheduling(Task task, Priority priority)
	// {
	// 	// Divide task to unit.
	// 	List<Unit>[] units = task.DivideToUnit();

	// 	// Validate units.
	// 	if(units == null)
	// 	{
	// 		System.out.println("[Error] Invalid task");
	// 		return queue.size();
	// 	}

	// 	// Enqueue units in queue with priority.
	// 	for(int i = 0; i < units.length; i++)
	// 	{
	// 		queue.enqueue(units[i], priority);
	// 	}

	// 	// Return queue size
	// 	return queue.size();
	// }



}

