package by.a1.unauthorizeddeliveries.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class SortDirectionUtilTest {
    @Nested
    class GetDirection {
        @Test
        void getDirectionShouldReturnAscendingSortForNullDirection() {
            Sort sort = Sort.by(Sort.Direction.ASC, "name");

            Sort result = SortDirectionUtil.getDirection(sort, null);

            Assertions.assertThat(result).isEqualTo(sort);
        }

        @Test
        void getDirectionShouldReturnDescendingSortForDescDirection() {
            Sort sort = Sort.by(Sort.Direction.ASC, "name");

            Sort result = SortDirectionUtil.getDirection(sort, "desc");

            Assertions.assertThat(result).isEqualTo(sort.descending());
        }

        @Test
        void getDirectionShouldReturnAscendingSortForNonDescDirection() {
            Sort sort = Sort.by(Sort.Direction.DESC, "name");

            Sort result = SortDirectionUtil.getDirection(sort, "asc");

            Assertions.assertThat(result).isEqualTo(sort.ascending());
        }
    }
}