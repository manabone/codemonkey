package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AbsUploadEntity;

@Service
public abstract class AbsUploadEntityServiceImpl<T extends AbsUploadEntity> extends PhysicalServiceImpl<T> implements AbsUploadEntityService<T> {

}
