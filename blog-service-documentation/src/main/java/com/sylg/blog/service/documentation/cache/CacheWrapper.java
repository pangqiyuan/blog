package com.sylg.blog.service.documentation.cache;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Cache wrapper.
 *
 * @author 忆地球往事
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class CacheWrapper<V> implements Serializable {

    /**
     * Cache data
     */
    private V data;

    /**
     * Expired time.
     */
    private LocalDateTime expireAt;

    /**
     * Create time.
     */
    private LocalDateTime createAt;
}
