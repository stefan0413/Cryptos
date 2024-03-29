package com.cryptos.apigateway.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter
{

	private static final String authenticationTokenType = "Bearer";
	private final UserDetailsService userDetailsService;
	private final JwtUtils jwtUtils;
	private final JwtBlacklistService jwtBlacklistService;

	public JWTAuthenticationFilter(UserDetailsService userDetailsService,
								   JwtUtils jwtUtils, JwtBlacklistService jwtBlacklistService)
	{
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.jwtBlacklistService = jwtBlacklistService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException
	{
		String authenticationHeader = request.getHeader("Authorization");
		String userEmail;
		String jwtToken;

		if (authenticationHeader == null || !authenticationHeader.startsWith(authenticationTokenType))
		{
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authenticationHeader.substring(7);
		userEmail = jwtUtils.extractUsername(jwtToken);



		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

			if (jwtUtils.validateToken(jwtToken,userDetails) && !jwtBlacklistService.isTokenBlacklisted(jwtToken))
			{
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		filterChain.doFilter(request,response);
	}
}
