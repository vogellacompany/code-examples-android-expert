package com.vogella.android.rxjava.simple.rxjavaintro;

import com.vogella.android.rxjava.simple.rxjavaintro.LessonResources.ComcastNetworkAdapter;
import com.vogella.android.rxjava.simple.rxjavaintro.LessonResources.Elevator;
import com.vogella.android.rxjava.simple.rxjavaintro.LessonResources.ElevatorPassenger;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;

public class lessonC_BooleanLogicAndErrorHandling {

	private static final Observable<?> ________ = null;
	public int mSum;
	public Boolean mBooleanValue;

	private int ____;
	private Object ______ = "";
	private Boolean _________;
	private TestObserver<Object> testObservable;

	Function<ElevatorPassenger, Boolean> _______ = elevatorPassenger -> false;
	Observable<Boolean> __________;
	private Object mThrowable;

	@Before
	public void setup() {
		testObservable = new TestObserver<>();
	}

	/**
	 * In this section we will learn about boolean logic we can apply to our
	 * pipelines of data. Our first stop on the tour is takeWhile(), similar in
	 * concept to the while loop you may already be familiar with.
	 * http://reactivex.io/documentation/operators/takewhile.html
	 * <p>
	 * In this experiment, we will load elevators with passengers eager to reach
	 * their destinations. One thing: Our elevator has a maximum capacity. If we
	 * overload it, our passengers may be injured or even die! We will use
	 * takeWhile to ensure no elevator is overloaded.
	 */
	@Test
	public void takeWhileShouldEvaluateAnExpressionAndEmitsEventsUntilItReturnsFalse() {

		LessonResources.Elevator elevator = new LessonResources.Elevator();

		Observable<ElevatorPassenger> elevatorQueueOne = Observable
				.fromIterable(Arrays.asList(new ElevatorPassenger("Max", 168), new ElevatorPassenger("Mike", 234),
						new ElevatorPassenger("Ronald", 192), new ElevatorPassenger("William", 142),
						new ElevatorPassenger("Jacqueline", 114)));

		Observable<ElevatorPassenger> elevatorQueueTwo = Observable
				.fromIterable(Arrays.asList(new ElevatorPassenger("Randy", 320), new ElevatorPassenger("Jerome", 125),
						new ElevatorPassenger("Sally-Joe", 349), new ElevatorPassenger("Little Eli", 54)));

		/**
		 * the takeWhile operator evaluates an expression each time a new item
		 * is emitted in the stream. As long as it returns true, the Observable
		 * stream of data takeWhile operates on continues to emit more
		 * data/events.
		 *
		 * Riddle: Lets define our elevator rule: the total weight of all
		 * passengers aboard an elevator may not be larger than 500 pounds.
		 * How!?! Hint: Check out the Public methods available on
		 * LessonResources.Elevator and passenger!
		 */
		Predicate<ElevatorPassenger> elevatorPredicateRule = passenger -> (elevator.getTotalWeightInPounds() + passenger.mWeightInPounds < Elevator.MAX_CAPACITY_POUNDS);
		/**
		 * Now all we need to do is to plug in the rule in takeWhile()
		 */
		elevatorQueueOne.takeWhile(elevatorPredicateRule).doOnNext(elevator::addPassenger).subscribe(testObservable);
		// Ensure we have a positive weight
		assertThat(elevator.getPassengerCount()).isGreaterThan(____);
		// Elevator weight should not be larger than Elevator.MAX_CAPACITY_POUNDS
		assertThat(elevator.getTotalWeightInPounds()).isLessThan(____);
		assertThat(elevator.getPassengerCount()).isEqualTo(2);
		System.out.println("elevator stats: " + elevator);
		/**
		 * One of the great advantages of using RxJava is that functions become
		 * composable: we can easily reuse existing pieces of the pipeline by
		 * plugging them into other pipelines. takeWhile() accepts a predicate
		 * or rule for determining
		 */
		elevator.unload();

		elevatorQueueTwo.takeWhile(elevatorPredicateRule).subscribe(elevator::addPassenger);
		assertThat(elevator.getPassengerCount()).isGreaterThan(0);
		assertThat(elevator.getTotalWeightInPounds()).isLessThan(Elevator.MAX_CAPACITY_POUNDS);
		assertThat(elevator.getPassengerCount()).isEqualTo(2);

		/**
		 * a (secret) Extra Challenge! Using what we've learned of rxJava so
		 * far, how could we get a list of passengers from elevatorQueueTwo that
		 * didn't make it into elevator?
		 */
		testObservable = new TestObserver<>();


		//
		// TODO solve the challenge above
		//
		assertThat(testObservable.values()).hasSize(2);
	}

	/**
	 * Next on our tour, we will see .amb(). Stands for Ambiguous - a somewhat
	 * mysterious name (traces its historical roots to the 60')! What it does is
	 * it moves forward with the first of a set of Observables to emit an event.
	 * <p>
	 * Useful in this situation below : 3 servers with the same data, but
	 * different response times. Give us the fastest!
	 */

	@Test
	public void ambShouldTakeTheFirstOfTwoObservablesToEmitData() {

		Integer randomInt = new Random().nextInt(100);
		Integer randomInt2 = new Random().nextInt(100);
		Integer randomInt3 = new Random().nextInt(100);

		//  we apply a function on our stream to find the smallest number
		Integer smallestNetworkLatency = Observable.just(randomInt, randomInt2, randomInt3).reduce(new BiFunction<Integer, Integer, Integer>() {
			@Override
			public Integer apply(Integer t1, Integer t2) throws Exception {
				return Math.min(t1, t2);
			}
		}).blockingGet();

		Observable<String> networkA = Observable.just("request took : " + randomInt + " millis").delay(randomInt,
				TimeUnit.MILLISECONDS);
		Observable<String> networkB = Observable.just("request took : " + randomInt2 + " millis").delay(randomInt2,
				TimeUnit.MILLISECONDS);
		Observable<String> networkC = Observable.just("request took : " + randomInt3 + " millis").delay(randomInt3,
				TimeUnit.MILLISECONDS);
		/**
		 * Do we have several servers that give the same data and we want the
		 * fastest of the two?
		 */
		List<Observable<String>> observables = Arrays.asList(networkA, networkB, networkC);
		// TODO use Observable.amb and subscribe your testObservable to observables

		// Once done uncomment this line
		//testObservable.awaitTerminalEvent();
		List<Object> onNextEvents = testObservable.values();
		assertThat(onNextEvents).contains("request took : " + smallestNetworkLatency + " millis");
		assertThat(onNextEvents).hasSize(1);

		// bonus! we can call .cache() on an operation that takes a while. It
		// will save the pipeline's events
		// up to the point that .cache() was called, saving them for use again.
		// http://reactivex.io/RxJava/javadoc/rx/Observable.html#cache()
		// networkA.cache().first();
	}

	/**
	 * The all operator collects everything emitted in the Observable, and then
	 * evaluates a predicate, which then emits true or false.
	 */

	@Test
	public void allShouldCheckPredicateOnAllValues() {
		Observable.just(2, 4, 6, 8, 9).all(integer -> integer % 2 == 0).subscribe(aBoolean -> mBooleanValue = aBoolean);
		assertThat(mBooleanValue).isEqualTo(null);
	}

	/**
	 * OK, it's time for a challenge! Given the range below and what we've
	 * learned of rxjava so far, how can we produce an mSum equal to 19?? Hint:
	 * Use a Predicate to filter all values small then 9 and use the reduce operator to combine the emitted values
	 */
	@Test
	public void filterAndReduceShouldResultIn19() {
		Observable<Integer> range = Observable.range(1, 10);
		// TODO use range and reduce on range and subscribe testObservable to it
		assertThat(testObservable.values().get(0)).isEqualTo(19);
	}

	/**
	 * So far we've dealt with a perfect world. Unfortunately the real world
	 * involves exceptions!
	 * <p>
	 * How do we respond to those exceptions in our program? Fortunately rxJava
	 * comes with many ways of handling these problems. Our first means to do
	 * this is with the .onError() event we can implement in our pipeline. This
	 * will receive whatever exception was emitted, so that we can log about it,
	 * take action, or notify the user for example.
	 */
	@Test
	public void onErrorShouldBeCalledWhenErrorsOccur() {
		List<String> arrayOne = new ArrayList<>();
		List<String> arrayTwo = new ArrayList<>();
		List<String> arrayThree = new ArrayList<>();
		Observable.just(arrayOne, arrayTwo, arrayThree).map(new Function<List<String>, List<String>>() {
			@Override
			public List<String> apply(List<String> strings) throws Exception {
				throw new RuntimeException("Something went wrong");
			}
		}).doOnError(oops -> mThrowable = oops).subscribe(testObservable);
		assertThat(mThrowable).isInstanceOf(null);
	}

	/**
	 * In this test, our flaky comcast modem is on the blink again
	 * unfortunately. .retry(long numberOfAttempts) can keep resubscribing to an
	 * Observable until a different non-error result occurs.
	 * http://reactivex.io/documentation/operators/retry.html
	 */
	@Test
	public void retryShouldPerformAnOperationWhichFailsMultipleTimesInTheHopesThatItMaySucceeed() {
		Observable<String> networkRequestObservable = Observable.just(new ComcastNetworkAdapter())
				.map(new Function<ComcastNetworkAdapter, String>() {
					@Override
					public String apply(ComcastNetworkAdapter networkAdapter) {
						return networkAdapter.getData().get(0);
					}
				}).repeat(100);
		// TODO check the model to find the correct retry mode
		networkRequestObservable.retry(____).subscribe(testObservable);
		assertThat(testObservable.values().get(0)).isEqualTo("extremely important data");
	}

	/**
	 * In this experiment, we will use RxJava to pick a lock. Our lock has three
	 * tumblers. We will need them all to be up to unlock the lock!
	 */

	@Test
	public void combineLatestShouldTakeTheLastEventsOfASetOfObservablesAndCombinesThem() {

		Observable<Boolean> tumbler1Observable = Observable.just(20).map(integer -> new Random().nextInt(integer) > 15)
				.delay(new Random().nextInt(20), TimeUnit.MILLISECONDS).repeat(1000);
		Observable<Boolean> tumbler2Observable = Observable.just(20).map(integer -> new Random().nextInt(integer) > 15)
				.delay(new Random().nextInt(20), TimeUnit.MILLISECONDS).repeat(1000);
		Observable<Boolean> tumbler3Observable = Observable.just(20).map(integer -> new Random().nextInt(integer) > 15)
				.delay(new Random().nextInt(20), TimeUnit.MILLISECONDS).repeat(1000);

		Function3<Boolean, Boolean, Boolean, Boolean> combineTumblerStatesFunction = (tumblerOneUp, tumblerTwoUp,
				tumblerThreeUp) -> {
			Boolean allTumblersUnlocked = tumblerOneUp && tumblerTwoUp && tumblerThreeUp;
			return allTumblersUnlocked;
		};

		Observable<Boolean> lockIsPickedObservable = Observable
				.combineLatest(tumbler1Observable, tumbler2Observable, tumbler3Observable, combineTumblerStatesFunction)
				.takeUntil(unlocked -> unlocked == true);
		lockIsPickedObservable.subscribe(testObservable);
		testObservable.awaitTerminalEvent();
		List<Object> onNextEvents = testObservable.values();
		assertThat(onNextEvents.get(onNextEvents.size()-1)).isEqualTo(null);
	}

}
