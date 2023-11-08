package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.model.ProfilePicture;
import com.generation.acadevmia.model.User;
import com.generation.acadevmia.repository.ProfilePictureRepository;
import com.generation.acadevmia.repository.RoleRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.jwt.JwtUtils;
import com.generation.acadevmia.security.services.UserDetailsImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public String getName() {
		String result = null;
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<UserEntity> u = userRepository.findByUsername(principal.getUsername());
		if (u.isPresent())
			return u.get().getName();
		return result;
    }

    public String getProfilePicture() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<UserEntity> u = userRepository.findByUsername(principal.getUsername());
		if (u.isEmpty())
			return null;
		Optional<ProfilePicture> pp = profilePictureRepository.findByUser(u.get());
		return pp.isPresent()? pp.get().getImg(): null;
    }

    public UserEntity updateName(String newName) {
		UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<UserEntity> u = userRepository.findByUsername(principal.getUsername());
		if (u.isPresent()) {
			UserEntity user = u.get();
			user.setName(newName);
			return userRepository.save(user);
		}
		return null;
    }

    public ProfilePicture updateProfilePicture(String base64Image) {
		UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<UserEntity> u = userRepository.findByUsername(principal.getUsername());
		if (u.isEmpty())
			return null;
		Optional<ProfilePicture> p = profilePictureRepository.findByUser(u.get());
		ProfilePicture pp;
		if (p.isEmpty()) {
			 pp = new ProfilePicture(null, base64Image, u.get());
			 return profilePictureRepository.save(pp);
		}
		pp = p.get();
		pp.setImg(base64Image);
		return profilePictureRepository.save(pp);
    }
}
