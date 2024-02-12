package com.io.tech.service;

import com.io.tech.entity.StockDetailsEntity;
import com.io.tech.pojo.StandardMessageResponse;
import com.io.tech.pojo.StockDetailsPojo;
import com.io.tech.repository.CompanyDetailsRepository;
import com.io.tech.repository.StockDetailsId;
import com.io.tech.repository.StockDetailsRepository;
import com.io.tech.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class StockDetailsService {

    @Autowired
    StockDetailsRepository stockRepository;

    @Autowired
    CompanyDetailsRepository companyRepository;

    private static StockDetailsEntity getBranchDetailsEntity(StockDetailsPojo pojo, StockDetailsEntity details) {
        StockDetailsEntity entity = new StockDetailsEntity();
        StockDetailsId id = new StockDetailsId();
        id.setStockId(details.getId().getStockId());
        id.setCompanyId(details.getId().getCompanyId());
        entity.setId(id);
        entity.setStockName(Validator.hasData(pojo.getStock_name()) ? pojo.getStock_name() : details.getStockName());
        entity.setPrice(Validator.hasData(pojo.getPrice()) ? pojo.getPrice() : details.getPrice());
        entity.setWarranty(Validator.hasData(pojo.getWarranty()) ? pojo.getWarranty() : details.getWarranty());
        entity.setTransferTo(Validator.hasData(pojo.getTransfer_to()) ? pojo.getTransfer_to() : details.getTransferTo());
        entity.setVendorName(Validator.hasData(pojo.getVendor_name()) ? pojo.getVendor_name() : details.getVendorName());
        entity.setVendorLocation(Validator.hasData(pojo.getVendor_location()) ? pojo.getVendor_location() : details.getVendorLocation());
        entity.setStatus(Validator.hasData(pojo.getStatus()) ? pojo.getStatus() : details.getStatus());
        return entity;
    }

    public StandardMessageResponse addStock(String companyId, String userId, StockDetailsPojo pojo) {
        try {
            if (!validateParams(companyId, pojo))
                return StandardResponseUtil.noDataMessage(Constants.REQUIRED_MISSING_PARAMETER);
            if (!Validator.hasData(companyRepository.findByCompanyId(companyId)))
                return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
            StockDetailsEntity stockDetails = stockRepository.findTopByIdCompanyIdOrderByYearDesc(companyId);
            String branchName = CacheUtil.getObjectFromCache(companyId + userId + Constants.BRANCH_SHORT_NAME);
            String stockIndex = (pojo.getManufacturer().equals(Constants.PRODUCTION)) ? branchName + "PR" : branchName + "GD";
            String year;
            if (Validator.hasData(stockDetails)) {
                String yearMonth = stockDetails.getYear().substring(0, 4);
                year = (yearMonth.equals(DateFormatter.getCurrentYearMonth())) ? String.valueOf(Integer.parseInt(stockDetails.getYear()) + 1) : DateFormatter.getCurrentYearMonth() + "0001";
            } else year = DateFormatter.getCurrentYearMonth() + "0001";
            addStockDetails(companyId, pojo, stockIndex, year);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            log.error("Error occurred in addStock service: " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    private void addStockDetails(String companyId, StockDetailsPojo pojo, String stockIndex, String year) {
        StockDetailsId id = new StockDetailsId();
        id.setStockId(stockIndex + year);
        id.setCompanyId(companyId);
        StockDetailsEntity entity = new StockDetailsEntity();
        entity.setId(id);
        entity.setStockName(pojo.getStock_name());
        entity.setPrice(pojo.getPrice());
        entity.setWarranty(pojo.getWarranty());
        entity.setVendorName(pojo.getVendor_name());
        entity.setVendorLocation(pojo.getVendor_location());
        entity.setTransferTo(pojo.getTransfer_to());
        entity.setStatus("In Stock");
        entity.setYear(year);
        stockRepository.save(entity);
    }

    public StandardMessageResponse getStockList(String companyId) {
        try {
            if (Validator.hasData(companyId)) {
                List<StockDetailsEntity> list = stockRepository.findByIdCompanyIdOrderByIdStockIdAsc(companyId);
                if (Validator.hasData(list))
                    return StandardResponseUtil.successResponse(list);
                else return StandardResponseUtil.successMessage(Constants.NO_DATA_FOUND);
            } else return StandardResponseUtil.noDataMessage("Required missing parameter");
        } catch (Exception e) {
            log.error("Error occurred in getStockList service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getStockDetails(String companyId, String stockId) {
        try {
            if (!Validator.hasData(stockId) && !Validator.hasData(companyId))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            StockDetailsEntity list = stockRepository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(Collections.singletonList(list));
            return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in getCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse getStockStatus(String companyId, String status) {
        try {
            if (!Validator.hasData(companyId) && !Validator.hasData(status))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            List<StockDetailsEntity> list = null;
            if (status.equals("All"))
                list = stockRepository.findByIdCompanyIdOrderByIdStockIdAsc(companyId);
            else
                list = stockRepository.findByIdCompanyIdAndStatus(companyId, status);
            if (Validator.hasData(list)) return StandardResponseUtil.successResponse(list);
            else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in findCompanyDetailsByStatus service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse updateBranchDetails(String companyId, StockDetailsPojo pojo) {
        try {
            if (!Validator.hasData(pojo.getStock_id()) && !Validator.hasData(companyId))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            StockDetailsEntity details = stockRepository.findByIdCompanyIdAndIdStockId(companyId, pojo.getStock_id());
            if (Validator.hasData(details)) {
                StockDetailsEntity entity = getBranchDetailsEntity(pojo, details);
                stockRepository.save(entity);
                return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_UPDATED);
            } else return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
        } catch (Exception e) {
            log.error("Error occurred in updateCompanyDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    public StandardMessageResponse removeStockDetails(String companyId, String stockId) {
        try {
            if (!Validator.hasData(stockId) && !Validator.hasData(companyId))
                return StandardResponseUtil.badRequestResponse(Constants.REQUIRED_MISSING_PARAMETER);
            StockDetailsEntity stockDetails = stockRepository.findByIdCompanyIdAndIdStockId(companyId, stockId);
            if (!Validator.hasData(stockDetails)) return StandardResponseUtil.noDataMessage(Constants.NO_DATA_FOUND);
            stockRepository.updateStatusByIdCompanyIdAndIdStockId(stockId, companyId, Constants.OUT_OF_STOCK);
            return StandardResponseUtil.successMessage(Constants.SUCCESSFULLY_REMOVED);
        } catch (Exception e) {
            log.error("Error occurred in removeStockDetails service : " + e.getMessage());
            return StandardResponseUtil.internalServerErrorResponse();
        }
    }

    //
    private boolean validateParams(String companyId, StockDetailsPojo pojo) {
        return Validator.hasData(companyId) && Validator.hasData(pojo.getManufacturer()) && Validator.hasData(pojo.getStock_name()) && Validator.hasData(pojo.getPrice()) && Validator.hasData(pojo.getVendor_name()) && Validator.hasData(pojo.getVendor_location()) && Validator.hasData(pojo.getWarranty()) && Validator.hasData(pojo.getTransfer_to());
    }
}
