package com.hospital.controller;

import com.hospital.model.Inventory;
import com.hospital.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String listInventory(Model model) {
        model.addAttribute("items", inventoryService.getAllInventory());
        return "inventory/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Inventory());
        return "inventory/form";
    }

    @PostMapping
    public String createInventory(@Valid @ModelAttribute("item") Inventory inventory, BindingResult result) {
        if (result.hasErrors()) return "inventory/form";
        inventoryService.saveInventory(inventory);
        return "redirect:/inventory";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        inventoryService.getInventoryById(id).ifPresent(i -> model.addAttribute("item", i));
        return "inventory/form";
    }

    @PostMapping("/{id}")
    public String updateInventory(@PathVariable String id, @Valid @ModelAttribute("item") Inventory inventory,
                                  BindingResult result) {
        if (result.hasErrors()) return "inventory/form";
        inventory.setId(id);
        inventoryService.saveInventory(inventory);
        return "redirect:/inventory";
    }

    @GetMapping("/{id}/delete")
    public String deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventory";
    }

    // Report: near expiration (within 30 days)
    @GetMapping("/near-expiration")
    public String nearExpiration(Model model) {
        model.addAttribute("items", inventoryService.getItemsNearExpiration());
        return "inventory/near-expiration";
    }
}
