package com.generation.acadevmia.service;

import com.generation.acadevmia.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreguntaService {

    @Autowired
    PreguntaRepository preguntaRepository;
}
