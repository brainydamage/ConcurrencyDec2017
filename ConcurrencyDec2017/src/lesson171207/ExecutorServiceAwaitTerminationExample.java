package lesson171207;

import lesson171201.Utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceAwaitTerminationExample {
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		service.execute(()->{
			Utils.pause(1000);
			System.out.println("one");
		});
		
		service.execute(()->{
			Utils.pause(1000);
			System.out.println("two");
		});
		
		service.execute(()->{
			Utils.pause(1000);
			System.out.println("three");
		});
		
		service.shutdown();

		boolean result = service.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("finished all tasks");
    }
}
