/**
 * 
 */
package br.edu.fanor.progweb.arquitetura.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.fanor.progweb.arquitetura.entity.Usuario;
import br.edu.fanor.progweb.arquitetura.to.SegurancaTO;

/**
 * @author patrick.cunha
 * @since 07/05/2015
 */
@Aspect
@Component
public class SecurityConfig {

	@Autowired
	private SegurancaTO segurancaTO;
	private static final Logger LOG = LoggerFactory
			.getLogger(SecurityConfig.class);
	private static final String SECURITY_TAG = "[SEGURANCA] ";

	@Before("@within(RolesAllowed) || @within(PermitAll) || @annotation(RolesAllowed) || @annotation(PermitAll)")
	public void checkSecurity(JoinPoint joinPoint) {

		Method metodo = ((MethodSignature) joinPoint.getSignature())
				.getMethod();

		// Verifica se o acesso ao metodo esta liberado.
		if (metodo.getAnnotation(PermitAll.class) != null) {
			return;
		}

		// Contexto de segurança do usuário logado
		Usuario usuario = this.segurancaTO.getUsuario();
		if (usuario == null || !this.segurancaTO.isAutenticado()) {
			this.dispararAcessoNegado();
			}
		}

	private void dispararAcessoNegado() {
		SecurityException se = new SecurityException(SECURITY_TAG + "Capturada uma tentativa de acesso indevido. Tentativa abortada.");
		LOG.debug(SECURITY_TAG + "Capturada uma tentativa de acesso indevido. Tentativa abortada.", se);

		throw se;
	}
	
}
