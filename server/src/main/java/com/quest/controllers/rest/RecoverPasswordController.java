package com.quest.controllers.rest;

import com.quest.models.Player;
import com.quest.models.UtilityModel;
import com.quest.repositories.PlayerRepository;
import com.quest.services.rest.PlayerServices;
import com.quest.config.constants.QuestConstants;

import jakarta.persistence.EntityNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.quest.controllers.rest.PlayerController;

import net.bytebuddy.utility.RandomString;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.util.*;


@Controller
@RequestMapping(value="/recoverPassword")
public class RecoverPasswordController{

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerServices playerServices;

	@Autowired
	private JavaMailSender mailSender;

	@GetMapping
	public ModelAndView recoverPasswordController(HttpSession session) {
		ModelAndView mv = new ModelAndView("recover-password");
		return mv;
	}

	// Método post que gera o token de recuperação de senha e realiza o envio do email de recuperação
	@ResponseBody
	@PostMapping(value="/generate-token")
	public ResponseEntity<String> recuperarSenha(jakarta.servlet.http.HttpServletRequest request, @RequestBody String playerEmail) {
		String emailTo = playerEmail.replaceAll("\"","");
		String token = RandomString.make(255);

		try {
			playerServices.updateRecoverPasswordToken(token, emailTo);

			String resetPasswordLink = UtilityModel.getSiteURL(request) + "/recoverPassword/" + token;
			
			sendEmail(playerEmail, resetPasswordLink);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

	private void sendEmail(String playerEmail, String resetPasswordLink) {
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper =  new MimeMessageHelper(message);

			helper.setFrom("nao.responda.quest2.fatecsp@gmail.com", "Quest2 - Não Responda");
			helper.setTo(playerEmail);

			String emailSubject = "Aqui está o link para redefinir sua senha.";

			String emailText = "Olá,\n"
							+ "Você solicitou a redefinição de senha.\n"
							+ "Clique no link abaixo para redefinir sua senha:\n"
							+ resetPasswordLink
							+ "\nIgnore este e-mail caso você se lembre de sua senha ou não fez a solicitação.";

			helper.setSubject(emailSubject);
			helper.setText(emailText, true);

			mailSender.send(message);
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}catch (MessagingException e){
			e.printStackTrace();
		}
	}

	// Método get que direciona o usuário para a página de alteração da senha a partir do token constante na url
	@GetMapping(value="/{token}")
	public String changePassword(@PathVariable String token){
		Player playerForChangePassword = playerServices.get(token);
		if(playerForChangePassword == null) {
			return null;
		}
		return "recover-password";
	}

	@ResponseBody
	@PostMapping(value="/{token}")
	public ResponseEntity<String> updatePlayer(@RequestBody String newPassword, @PathVariable String token) {
		try {
			Player player = this.playerServices.get(token);

			JSONObject jsonObject = new JSONObject(newPassword);

			String password = jsonObject.getString("senha");

			Player playerObj = this.playerRepository.findById(player.getId())
					.orElseThrow(() -> new EntityNotFoundException("Player not found with provided Id"));

			playerObj.setPassword(newPassword);
			playerObj.setRecoverPasswordToken(null);
			this.playerRepository.save(playerObj);

			return ResponseEntity.status(HttpStatus.OK).body("ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}