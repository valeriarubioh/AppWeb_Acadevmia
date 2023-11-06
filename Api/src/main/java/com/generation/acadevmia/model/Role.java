package com.generation.acadevmia.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document(collection = "roles")
public class Role {
  @Id
  private String id;
  private ERole name;
}
