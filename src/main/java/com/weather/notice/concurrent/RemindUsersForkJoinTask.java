package com.weather.notice.concurrent;

import java.util.concurrent.RecursiveAction;

public class RemindUsersForkJoinTask extends RecursiveAction {
	private static final long serialVersionUID = -2517658213036853436L;
	private final long threshold = 3000L;

	private RemindUsers problem;

	public RemindUsersForkJoinTask(RemindUsers problem) {
		super();
		this.problem = problem;
	}

	@Override
	protected void compute() {
		if (this.problem.getN() <= threshold) {
			this.problem.remind();
		} else {
			long middlePoint = this.problem.getN() / 2;
			RemindUsersForkJoinTask worker1 = new RemindUsersForkJoinTask(
					this.problem.newSub(0L, middlePoint - 1));
			RemindUsersForkJoinTask worker2 = new RemindUsersForkJoinTask(
					this.problem.newSub(middlePoint, this.problem.getN() - 1));
			invokeAll(worker1, worker2);
		}
	}
}
