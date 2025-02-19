package com.learn.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * VERB+NOUN+Query
 */

@Value
@Builder
@AllArgsConstructor
//We don't need to use @Data because we don't set this field
//We only use it so get is more than enough
public class FindProfileQuery
{
    private final String mobileNumber;

}
