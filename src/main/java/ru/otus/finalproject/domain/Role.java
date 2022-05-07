package ru.otus.finalproject.domain;

public enum Role {
    ADMIN, MANAGER, USER;

    /*public Set<SimpleGrantedAuthority> getAuthorities() {
        return new HashSet<>().stream()
                .map(permission -> new SimpleGrantedAuthority(ADMIN.name()))
                .collect(Collectors.toSet());
    }*/
}
