package com.ktdsuniversity.edu.pms.product.service;

import com.ktdsuniversity.edu.pms.product.vo.ProductListVO;
import com.ktdsuniversity.edu.pms.product.vo.ProductManagementVO;
import com.ktdsuniversity.edu.pms.product.vo.ProductVO;

public interface ProductService {

	/**
	 * 비픔 목록과 비품 수를 모두 조회
	 * @return
	 */
	public ProductListVO getAllProduct(ProductVO productVO);

	public boolean createNewProduct(ProductVO productVO);

	public ProductVO getOneProduct(String id);

	public boolean updateOneProduct(String prdtId);


	
	

}
