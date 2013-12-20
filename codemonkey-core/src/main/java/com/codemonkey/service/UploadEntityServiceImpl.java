package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.UploadEntity;

@Service
public class UploadEntityServiceImpl extends AbsUploadEntityServiceImpl<UploadEntity> implements UploadEntityService {

	@Override
	public UploadEntity createEntity() {
		return new UploadEntity();
	}

}
