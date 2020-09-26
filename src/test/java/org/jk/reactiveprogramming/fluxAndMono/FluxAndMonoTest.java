package org.jk.reactiveprogramming.fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {
    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                /*.concatWith(Flux.error(new Throwable("Intentionally throwing error.")))*/
                .concatWith(Flux.just("After Error"))
                .log();
        // The only way of accessing elements from a flux is by subscribing to it.
        // without subscribing there is no meaning of flux and it's useless.
        stringFlux.subscribe(System.out::println,
                e -> System.err.println(e),
                () -> System.out.println("Completed"));
    }
}
