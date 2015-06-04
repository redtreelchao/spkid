package com.fclub.tpd.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.MamiTuanService;
import com.fclub.tpd.dataobject.MamiTuan;
import com.fclub.tpd.mapper.MamiTuanMapper;

@Service
public class MamiTuanServiceImpl implements MamiTuanService {

	@Autowired
	private MamiTuanMapper mamiTuanMapper;
	
	@Override
	public int add(MamiTuan tuan) {
		return mamiTuanMapper.insert(tuan);
	}

	@Override
	public int remove(Integer tuanId) {
		return mamiTuanMapper.delete(tuanId);
	}

	@Override
	public int update(MamiTuan tuan) {
		return mamiTuanMapper.update(tuan);
	}

	@Override
	public MamiTuan findById(Integer tuanId) {
		return mamiTuanMapper.get(tuanId);
	}

	@Override
	public List<MamiTuan> queryByPage(Page<MamiTuan> page, MamiTuan tuan) {
		return mamiTuanMapper.findPage(page, tuan);
	}

	@Override
	public boolean checkByProductId(Integer productId) {
		return mamiTuanMapper.getCountByProductId(productId) > 0;
	}

}
