package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ToyRepository클래스의")
class ToyRepositoryTest {
    private final Long toyId = 1L;
    private final String toyName = "장난감 칼";
    private final String toyBrand = "코드숨";
    private final int toyPrice = 5000;
    private final String toyImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";

    @Autowired
    private ToyRepository toyRepository;
    private Toy toy;

    @BeforeEach
    void setUp() {
        toyRepository.deleteAll();
        toy = new Toy(toyName, toyBrand, toyPrice, toyImageUrl);
    }

    private void saveToy() {
        toyRepository.save(toy);
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 toy가 없다면")
        class Context_without_any_toy {
            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_empty_list() {
                assertThat(toyRepository.findAll().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("저장된 toy가 있다면")
        class Context_with_a_toy {
            @BeforeEach
            void setToyList() {
                saveToy();
            }

            @Test
            @DisplayName("toy 리스트를 리턴한다.")
            void it_return_toy_list() {
                List<Toy> toyList = toyRepository.findAll();

                assertThat(toyList.size()).isGreaterThanOrEqualTo(1);
            }
        }
    }
}