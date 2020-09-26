package org.jk.reactiveprogramming.fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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

    @Test
    public void fluxTestElements_WithoutErrors(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithErrors(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new Throwable("Intentionally throwing error.")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
//                .expectError(Throwable.class)
                .expectErrorMessage("Intentionally throwing error.")
                .verify();
    }

    @Test
    public void fluxTestElementCount_WithErrors(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new Throwable("Intentionally throwing error.")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Intentionally throwing error.")
                .verify();
    }

    @Test
    public void fluxTestElements_WithErrors1(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new Throwable("Intentionally throwing error.")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectErrorMessage("Intentionally throwing error.")
                .verify();
    }
}
