package com.guna.yumzoom.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodMapper foodMapper;
    private final FoodRepo foodRepo;




}
