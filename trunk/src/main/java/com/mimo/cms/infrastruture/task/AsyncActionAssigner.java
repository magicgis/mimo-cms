package com.mimo.cms.infrastruture.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author loudyn
 * 
 */
public class AsyncActionAssigner {

	/**
	 * 
	 * @author loudyn
	 *
	 */
	public static final class AsyncToken {
		private CountDownLatch finishSignal;
		private int count;

		private AsyncToken(int count) {
			this.count = count;
			this.finishSignal = new CountDownLatch(count);
		}

		/**
		 * 
		 * @return
		 */
		public boolean isFinished() {
			return finishSignal.getCount() == this.count;
		}

		/**
		 * 
		 * @param timeout
		 * @param unit
		 * @throws InterruptedException
		 */
		public void awaitTerminal(long timeout, TimeUnit unit) throws InterruptedException {
			finishSignal.await(timeout, unit);
		}

		private void finishOne() {
			finishSignal.countDown();
		}

	}

	private final static ExecutorService executor = Executors.newFixedThreadPool(100);

	/**
	 * 
	 * @param action
	 * @return
	 */
	public static AsyncToken assign(final AsyncAction action) {
		return compoAssign(action);
	}

	/**
	 * 
	 * @param actions
	 * @return
	 */
	public static AsyncToken compoAssign(AsyncAction... actions) {
		final AsyncToken token = new AsyncToken(actions.length);

		for (final AsyncAction action : actions) {

			executor.submit(new Runnable() {

				@Override
				public void run() {
					try {

						action.execute();
					} catch (Exception e) {
						action.caughtException(e);
					} finally {
						token.finishOne();
					}
				}
			});
		}

		return token;
	}
}
