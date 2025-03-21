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

    @Operation(summary = "Lista de cuentas de usuario", description = "Método para solicitar la lista de cuentas, asociadas a un usuario.")
    @RequestMapping(value = "/account/{id}/owner/{owner_id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAnAccount(@Min(1) @PathVariable("id") Long pid, @Min(1) @PathVariable("owner_id") Long cid);

    @RequestMapping(value = "/accounts/owner/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAccountsByOwnerId(@Min(1) @PathVariable("id") Long pid);

    @PostMapping(value = "/account/customer/{owner_id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity createAccount(@Valid @RequestBody Account account, @Min(1) @PathVariable("owner_id") Long ownerId);

    @PutMapping(value = "/account/{id}/customer/{owner_id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateAccount(@Min(1) @PathVariable("id") Long id, @Valid @RequestBody Account account, @Min(1) @PathVariable("owner_id") Long ownerId);

    @DeleteMapping(value = "/account/{id}/owner/{owner_id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity deleteAccount(@Min(1) @PathVariable("id") Long id,  @Min(1) @PathVariable("owner_id") Long cid);

    @PutMapping(value = "/account/{id}/deposit", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateDepositAccount(@Min(1) @PathVariable("id") Long id, @Valid @RequestBody Account account);

    @PutMapping(value = "/account/{id}/withdraw", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateWithdrawAccount(@Min(1) @PathVariable("id") Long id, @Valid @RequestBody Account account);

    @DeleteMapping(value = "/accounts/owner/{owner_id}",  consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity deleteAccountsByOwnerId(@Min(1) @PathVariable("owner_id") Long pid);

    @RequestMapping(value = "/account/owner/{owner_id}/loan/{amount}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateWithdrawAccount(@Min(1) @PathVariable("owner_id") Long id, @Min(1) @PathVariable("amount") Float amount);



}

