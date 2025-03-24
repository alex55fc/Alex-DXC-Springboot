package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/default")
@Validated
@Tag(name = "Accounts", description = "Gestión de cuentas")
public interface IAccountController {

    @Operation(summary = "Devuelve la cuenta de un usuario", description = "Método para solicitar la cuenta de un usuario.")
    @RequestMapping(value = "/account/{id}/customer/{owner_id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAnAccount(@Min(1) @PathVariable("id") Long pid, @Min(1) @PathVariable("owner_id") Long cid);

    @Operation(summary = "Lista de cuentas de usuario", description = "Método para solicitar la lista de cuentas, asociadas a un usuario.")
    @RequestMapping(value = "/accounts/customer/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAccountsByOwnerId(@Min(1) @PathVariable("id") Long pid);

    @Operation(summary = "Crear una cuenta de usuario", description = "Método para crear la cuenta del usuario.")
    @PostMapping(value = "/account/customer/{owner_id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity createAccount(@Valid @RequestBody Account account, @Min(1) @PathVariable("owner_id") Long ownerId);

    @Operation(summary = "Actualizar una cuenta de usuario", description = "Método para actualizar la cuenta del usuario.")
    @PutMapping(value = "/account/{id}/customer/{owner_id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateAccount(@Min(1) @PathVariable("id") Long id, @Valid @RequestBody Account account, @Min(1) @PathVariable("owner_id") Long ownerId);

    @Operation(summary = "Borrar una cuenta de usuario", description = "Método para borrar la cuenta del usuario.")
    @DeleteMapping(value = "/account/{id}/customer/{owner_id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity deleteAccount(@Min(1) @PathVariable("id") Long id,  @Min(1) @PathVariable("owner_id") Long cid);

    @Operation(summary = "Depositar en una cuenta de usuario", description = "Método para borrar la cuenta del usuario.")
    @PutMapping(value = "/account/{id}/deposit/{amount}/customer/{owner_id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateDepositAccount(@Min(1) @PathVariable("id") Long id, @Min(1) @PathVariable("amount") int amount, @Min(1) @PathVariable("owner_id") Long ownerId);

    @Operation(summary = "Retirar en una cuenta de usuario", description = "Método para borrar la cuenta del usuario.")
    @PutMapping(value = "/account/{id}/withdraw/{amount}/customer/{owner_id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateWithdrawAccount(@Min(1) @PathVariable("id") Long id, @Min(1) @PathVariable("amount") int amount, @Min(1) @PathVariable("owner_id") Long ownerId);

    @Operation(summary = "Borrar las cuentas de usuario", description = "Método para borrar las cuentas del usuario.")
    @DeleteMapping(value = "/accounts/customer/{owner_id}",  produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity deleteAccountsByOwnerId(@Min(1) @PathVariable("owner_id") Long pid);

    @Operation(summary = "Solicita un prestamo", description = "Método para comprobar si el usuario puede")
    @RequestMapping(value = "/account/customer/{owner_id}/loan/{amount}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity requestLoan(@Min(1) @PathVariable("owner_id") Long id, @Min(1) @PathVariable("amount") int amount);
}

