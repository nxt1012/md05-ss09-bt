package com.ra.md05ss09bt.payload.response;

import com.ra.md05ss09bt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
}
