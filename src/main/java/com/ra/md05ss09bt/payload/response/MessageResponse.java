package com.ra.md05ss09bt.payload.response;

import com.ra.md05ss09bt.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private User user;
    private String token;
    private final String TYPE = "Bearer ";
}
