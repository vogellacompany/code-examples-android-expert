package com.vogella.android.rxjava.simple.rxjavaintro;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static org.assertj.core.api.Assertions.assertThat;

public class lessonA_CreatingObservableStreams {

	private String _____;
	private int ____;
	private Integer mSum;
	private TestObserver<Object> testObserver;
	private int mCount1;
	private int mCount2;
	private int mCount3;
	private String result ="";

	@Before
	public void setup() {
		testObserver = new TestObserver<>();
		result ="";
	}

	
	/**
	 * Observables are ultimately about handling "streams" of items (i.e. more
	 * than one item) in a "data pipeline". Each item is called an "event" of
	 * "data". Here we have the creation of a new stream of data/events, called
	 * an Observable. (http://reactivex.io/RxJava/javadoc/rx/Observable.html) We
	 * also have a subscription, which finally takes the values from the
	 * pipeline and consumes them.
	 * <p>
	 * For our RxJava tests, we will be working with an object called
	 * TestObserver which the framework includes. It gives us an easy way to
	 * check what was emitted on the pipeline.
	 */
	@Test
	public void anObservableStreamOfEventsAndDataShouldEmitsEachItemInOrder() {

		Observable<String> pipelineOfData = Observable.just("Foo", "Bar");

		pipelineOfData.subscribe(testObserver);

		List<Object> dataEmitted = testObserver.values();
		assertThat(dataEmitted).hasSize(____);
		assertThat(dataEmitted).containsOnlyOnce(_____);
		assertThat(dataEmitted).containsOnlyOnce(_____);
	}
	
	@Test
	public void demonstrateObservableshouldCallObserversMethods() {

		String[] names = new String[] { "Lars", "Simon", "Jennifer", "Fabian", "David" };
		Disposable subscribe = Observable.fromArray(names).subscribe(next -> {
			result+=next;
		}, error -> {
			result +=error;
			System.out.println("onError " +  error);
		}, () -> {
			result+="onComplete";
		});
		assertThat(result).isEqualTo(_____);
	}

	/**
	 * Subjects can be both observables and observers
	 * Registration of the subscribers is important, a observer is only be notified if
	 * a new events occurs
	 * */
	@Test
	public void useSubject() {
		Subject<String> subject = PublishSubject.<String>create().toSerialized();
		subject.subscribe(e-> result+=e);
		subject.onNext("Hello");
		subject.onNext("Man");
		subject.onNext("Test");

		subject.subscribe(e-> doIt());
		subject.onNext("Test");
		assertThat(result).isEqualTo(_____);

	}
	
	private void doIt() {
		result+="Testing";
	}
	/**
	 * An observable stream calls 3 major lifecycle methods as it does it's
	 * work: onNext(), onCompleted(), and onError().
	 * <p>
	 * onNext(): An Observable calls this method whenever the Observable emits
	 * an item. This method takes as a parameter the item emitted by the
	 * Observable.
	 * <p>
	 * onError(): An Observable calls this method to indicate that it has failed
	 * to generate the expected data or has encountered some other error. This
	 * stops the Observable and it will not make further calls to onNext or
	 * onCompleted. The onError method takes as its parameter an indication of
	 * what caused the error.
	 * <p>
	 * onComplete(): An Observable calls this method after it has called onNext
	 * for the final time, if it has not encountered any errors.
	 */
	@Test
	public void _anObservableStreamShouldEmitsThreeMajorEventTypes() {
		Observable<Integer> pipelineOfData = Observable.just(1, 2, 3, 4, 5);
		pipelineOfData.doOnNext(integer -> mCount1++).doOnComplete(() -> mCount2++).doOnError(throwable -> mCount3++)
				.subscribe(testObserver);
		pipelineOfData.subscribe(integer -> System.out.println(integer));
		testObserver.awaitTerminalEvent();
		assertThat(mCount1).isEqualTo(____);
		assertThat(mCount2).isEqualTo(____);
		assertThat(mCount3).isEqualTo(____);
	}

	/**
	 * In the test above, we saw Observable.just(), which takes one or several
	 * Java objects and converts them into an Observable which emits those
	 * objects. (http://reactivex.io/RxJava/javadoc/rx/Observable.html#just(T))
	 * Let's build our own this time.
	 */
	@Test
	public void justShouldCreateAnObservableAndEmitsItsArguments() {

		String stoogeOne = "Larry";
		String stoogeTwo = "Moe";
		String stoogeThree = "Curly";
		Integer stoogeAge = 38;

		Observable<Object> stoogeDataObservable = Observable.just(stoogeOne, stoogeTwo, stoogeThree, stoogeAge);
		stoogeDataObservable.subscribe(testObserver);
		/**
		 * As we've seen, the TestObserver's values() method gives a list of all
		 * the events emitted by the observable stream in a blocking fashion.
		 * This makes it possible for us to test what was emitted by the stream.
		 * Without the TestObserver, the events would have been emitted
		 * asynchronously and our assertion would have failed.
		 */
		List<Object> events = testObserver.values();
		assertThat(events).containsOnlyOnce(_____);
		assertThat(events).containsOnlyOnce(_____);
		assertThat(events).containsOnlyOnce(_____);
		assertThat(events).containsOnlyOnce(_____);
		assertThat(events).hasSize(____);
	}

	/**
	 * Observable.fromIterable() is another way to create an Observable. It's
	 * different than .just() - it is specifically designed to work with
	 * Collections. When just is given a collection, it converts it into an
	 * Observable that emits each item from the list. Let's understand how the
	 * two are different more clearly.
	 */
	@Test
	public void fromIterableShouldCreateAnObservableThatEmitsEachElementFromAnIterable() {
		
		List<String> sandwichIngredients = Arrays.asList("bread (one)", "bread (two)", "cheese", "mayo", "turkey",
				"lettuce", "pickles", "jalapenos", "Sriracha sauce");
		Observable<String> favoriteFoodsObservable = Observable.fromIterable(sandwichIngredients);
		TestObserver<String> testObserverIteratable = null; // TODO create new TestObserver and subscribe to favoriteFoodsObservable

		assertThat(testObserverIteratable.values()).hasSize(____);
		assertThat(testObserverIteratable.values()).containsAll(null);


		TestObserver<List<String>> testObserverForJust = new TestObserver<>();
		// TODO create obseverable for sandwichIngredients with just operator and subscribe testObserverForJust to it
		assertThat(testObserverForJust.values()).hasSize(1);
		assertThat(testObserverForJust.values()).contains(sandwichIngredients);
		/**
		 * ^^ As you can see here, fromIterable() & just() do very different things!
		 */
	}

	/**
	 * So far we've created observables and immediately "subscribed" to them.
	 * Its only when we subscribe to an observable that it is fully wired up.
	 * This observable is now considered "hot". Until then it is "cold" and
	 * doesn't really do anything, it won't emit any events.
	 * <p>
	 * So if we are going to build an observable and not subscribe to it until
	 * later on, how can we include the all of the functionality as before? Do
	 * we have to put all the work inside subscribe() ? No we don't!
	 * <p>
	 * If we peek at the Observer interface we see it has four methods:
	 * <p>
	 * public interface Observer<T> { void onComplete(); void onError(Throwable
	 * var1); void onNext(T var1); onSubscribe
	 * <p>
	 * When we subscribe to an Observable, the code we put inside subscribe() is
	 * getting handed off to the Observer's onNext() method. However, we can
	 * manually pass code right to onNext() ourselves with Observable.doOnNext()
	 * <p>
	 * Lets setup an Observable with all the functionality we need to sum a
	 * range of Integers. Then lets subscribe to it later on.
	 */
	@Test
	public void observableShouldNotEmittedUntilAObserverSubscribes() {
		mSum = 0;
		/**
		 * Observable.range() creates a sequential list from a starting number
		 * of a particular size.
		 * (http://reactivex.io/RxJava/javadoc/rx/Observable.html#range(int,%20int))
		 *
		 * We also haven't seen doOnNext() yet - its one way we can take action
		 * based on one of a series of Observable lifecycle events.
		 * http://reactivex.io/documentation/operators/do.html
		 */
		Observable<Integer> numbers = Observable.range(1, 10).doOnNext(integer -> mSum += integer);
		// TODO make the numbers observable emits its data
		// Hint: what would we need to do to get our Observable to start
		// emitting things?
		assertThat(mSum).isEqualTo(_____);
	}

}
