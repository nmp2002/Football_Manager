package com.ttisv.dao.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Parameter;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ttisv.bean.system.TblBooking;
import com.ttisv.bean.system.TblRevenue;
import com.ttisv.common.utils.StringUtils;
import com.ttisv.dao.impl.BaseDaoImpl;

@Repository
public class TblBookingDaoImpl extends BaseDaoImpl<TblBooking> implements TblBookingDao {

    @Override
    public TblBooking saveorUpdate(TblBooking booking) {
        Session session = this.getCurrentSession();
        try {
            if (booking != null) {
                if (booking.getBookingId() != null) {
                    // Check if the entity already exists
                    TblBooking existingBooking = session.get(TblBooking.class, booking.getBookingId());
                    if (existingBooking != null) {
                        // Update the existing entity
                        existingBooking.setFieldId(booking.getFieldId());
                        existingBooking.setGuestId(booking.getGuestId());
                        existingBooking.setShiftFieldId(booking.getShiftFieldId());
                        existingBooking.setSmallFieldId(booking.getSmallFieldId());
                        existingBooking.setNameField(booking.getNameField());
                        existingBooking.setSmallFieldName(booking.getSmallFieldName());
                        existingBooking.setNameGuest(booking.getNameGuest());
                        existingBooking.setPhoneNumberGuest(booking.getPhoneNumberGuest());
                        existingBooking.setTotalPayment(booking.getTotalPayment());
                        existingBooking.setTimeStart(booking.getTimeStart());
                        existingBooking.setDay(booking.getDay());
                        existingBooking.setTimeEnd(booking.getTimeEnd());
                        existingBooking.setStatusField(booking.getStatusField());
                        existingBooking.setPaymentStatus(booking.getPaymentStatus());
                        existingBooking.setFieldType(booking.getFieldType());
                        session.merge(existingBooking);
                    } else {
                        // Save as a new entity
                        session.save(booking);
                    }
                } else {
                    // Save as a new entity
                    session.save(booking);
                }
                session.flush(); // Ensure changes are persisted immediately
                return booking;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public Page<TblBooking> getListPageBooking(Pageable pageable, Map<String, String> map) {
	    Session session = this.getCurrentSession();
	    Page<TblBooking> rs =null;
	    try {
	        int pageSize = pageable.getPageSize();
	        int currentPage = pageable.getPageNumber();
	        int startItem = currentPage * pageSize;
	        
	        String sql = "SELECT f FROM TblBooking f WHERE 1=1";
		
	
	        if (!StringUtils.isEmpty(map.get("nameField"))) {
	            sql += " and lower(f.nameField) like :nameField ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("smallFieldName"))) {
	            sql += " and lower(f.smallFieldName) like :smallFieldName ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("nameGuest"))) {
	            sql += " and lower(f.nameGuest) like :nameGuest ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("phoneNumberGuest"))) {
	            sql += " and lower(f.phoneNumberGuest) like :phoneNumberGuest ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("totalPayment"))) {
	            sql += " and lower(f.totalPayment) like :totalPayment ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("day"))) {
	            sql += " and lower(f.day) like :day ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("timeStart"))) {
	            sql += " and lower(f.timeStart) like :timeStart ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("timeEnd"))) {
	            sql += " and lower(f.timeEnd) like :timeEnd ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("paymentStatus"))) {
	            sql += " and lower(f.paymentStatus) like :paymentStatus ESCAPE '/'";
	        }

	        Query query = session.createQuery(sql, TblBooking.class);
	        query.setFirstResult(startItem);
	        query.setMaxResults(pageSize);

	        Set<Parameter<?>> params = query.getParameters();
	    	for (Parameter<?> parameter : params) {
				if (Objects.equals(parameter.getName(), "nameField") 
						|| Objects.equals(parameter.getName(), "smallFieldName")  
						|| Objects.equals(parameter.getName(), "nameGuest")
						|| Objects.equals(parameter.getName(), "phoneNumberGuest")
						|| Objects.equals(parameter.getName(), "totalPayment")
						|| Objects.equals(parameter.getName(), "day")
						|| Objects.equals(parameter.getName(), "timeStart")
						|| Objects.equals(parameter.getName(), "timeEnd")
						|| Objects.equals(parameter.getName(), "paymentStatus"))
				{
					query.setParameter(parameter.getName(),
							StringUtils.toLikeAndLowerCaseString(map.get(parameter.getName())));
				} else {
					query.setParameter(parameter.getName(), map.get(parameter.getName()));
				}
			}

	        List<TblBooking> lst = query.getResultList();
	        int count = this.countBookings(map);  
	        rs = new PageImpl<>(lst, PageRequest.of(currentPage, pageSize), count);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return rs;
	}
	public int countBookings(Map<String, String> map) {
	    Session session = this.getCurrentSession();
	    int rs = 0;
	    try {
	        String sql = "SELECT COUNT(*) FROM TblBooking f WHERE 1=1";


	        if (!StringUtils.isEmpty(map.get("nameField"))) {
	            sql += " and lower(f.nameField) like :nameField ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("smallFieldName"))) {
	            sql += " and lower(f.smallFieldName) like :smallFieldName ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("nameGuest"))) {
	            sql += " and lower(f.nameGuest) like :nameGuest ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("phoneNumberGuest"))) {
	            sql += " and lower(f.phoneNumberGuest) like :phoneNumberGuest ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("totalPayment"))) {
	            sql += " and lower(f.totalPayment) like :totalPayment ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("day"))) {
	            sql += " and lower(f.day) like :day ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("timeStart"))) {
	            sql += " and lower(f.timeStart) like :timeStart ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("timeEnd"))) {
	            sql += " and lower(f.timeEnd) like :timeEnd ESCAPE '/'";
	        }
	        if (!StringUtils.isEmpty(map.get("paymentStatus"))) {
	            sql += " and lower(f.paymentStatus) like :paymentStatus ESCAPE '/'";
	        }


	        Query query = session.createQuery(sql, Long.class);
	        Set<Parameter<?>> params = query.getParameters();
	    	for (Parameter<?> parameter : params) {
				if (Objects.equals(parameter.getName(), "nameField") || Objects.equals(parameter.getName(), "nameGuest")
						|| Objects.equals(parameter.getName(), "phoneNumberGuest")
						|| Objects.equals(parameter.getName(), "smallFieldName")  
						|| Objects.equals(parameter.getName(), "totalPayment")
						|| Objects.equals(parameter.getName(), "day")
						|| Objects.equals(parameter.getName(), "timeStart")
						|| Objects.equals(parameter.getName(), "timeEnd")
						|| Objects.equals(parameter.getName(), "paymentStatus"))
				{
					query.setParameter(parameter.getName(),
							StringUtils.toLikeAndLowerCaseString(map.get(parameter.getName())));
				} else {
					query.setParameter(parameter.getName(), map.get(parameter.getName()));
				}
			}

	    	rs = Integer.valueOf(String.valueOf(query.getSingleResult()));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return rs;
	}

	@Override
	public List<TblBooking> getBookingbySmallFieldId(Long smallFieldId) {
		List<TblBooking> result = null;
		try {
			Session session = this.getCurrentSession();
			String sql ="select u FROM TblBooking u WHERE u.smallFieldId =:smallFieldId";
			Query query = session.createQuery(sql).setParameter("smallFieldId", smallFieldId);
		    result = query.getResultList();
		   } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return result;
		}

	@Override
	public TblBooking getBookingById(Long bookingId) {
		TblBooking result = null;
		try {
			Session session = this.getCurrentSession();
			String sql = "select u FROM TblBooking u WHERE u.bookingId =:bookingId";
			Query query = session.createQuery(sql).setParameter("bookingId", bookingId);
			result = (TblBooking) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean deleteBooking(Long bookingId) {
	    Session session = this.getCurrentSession();
	    try {
	        TblBooking booking = session.get(TblBooking.class, bookingId);
	        if (booking != null) {
	            session.delete(booking);
	            session.flush(); 
	            return true; 
	        } else {
	            return false; 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; 
	    }
	}

	@Override
	public List<TblRevenue> getWeeklyRevenueByFieldId(Long fieldId) {
	    List<TblRevenue> result = new ArrayList<>();
	    try {
	        Session session = this.getCurrentSession();
	        String sql = "SELECT TO_CHAR(b.day, 'IW') as period, SUM(b.totalPayment) as totalPayment " +
	                     "FROM tbl_booking b " +
	                     "WHERE b.field_id = :fieldId AND b.status_field = 'booked' " +
	                     "GROUP BY TO_CHAR(b.day, 'IW')";
	        Query query = session.createNativeQuery(sql);
	        query.setParameter("fieldId", fieldId);

	        List<Object[]> queryResult = query.getResultList();
	        for (Object[] row : queryResult) {
	            String period = (String) row[0];
	            Long totalPayment = ((Number) row[1]).longValue(); // Chuyển đổi giá trị tổng tiền về kiểu Long
	            TblRevenue revenue = new TblRevenue(null, period, totalPayment);
	            result.add(revenue);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}


	
	@Override
	public List<TblRevenue> getMonthlyRevenueByFieldId(Long fieldId) {
	    List<TblRevenue> result = new ArrayList<>();
	    try {
	        Session session = this.getCurrentSession();
	        String sql = "SELECT TO_CHAR(b.day, 'MM') as period, SUM(b.totalPayment) as totalPayment " +
	                     "FROM tbl_booking b " +
	                     "WHERE b.field_id = :fieldId AND b.status_field = 'booked' " +
	                     "GROUP BY TO_CHAR(b.day, 'MM')";
	        Query query = session.createNativeQuery(sql);
	        query.setParameter("fieldId", fieldId);

	        List<Object[]> queryResult = query.getResultList();
	        for (Object[] row : queryResult) {
	            String period = (String) row[0];
	            Long totalPayment = ((Number) row[1]).longValue(); 
	            TblRevenue revenue = new TblRevenue(null, period, totalPayment);
	            result.add(revenue);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	@Override
	public List<TblBooking> getBookingbyFieldId(Long fieldId) {
		// TODO Auto-generated method stub
		List<TblBooking> result = null;
		try {
			Session session = this.getCurrentSession();
			String sql ="select u FROM TblBooking u WHERE u.fieldId =:fieldId";
			Query query = session.createQuery(sql).setParameter("fieldId", fieldId);
		    result = query.getResultList();
		   } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return result;
		}

	@Override
	public Boolean checkBookingExistence(Long smallFieldId, String timeStart, Date day) {
	    Session session = this.getCurrentSession();
	    Boolean exists = false;
	    try {
	        String sql = "SELECT COUNT(b) FROM TblBooking b WHERE b.smallFieldId = :smallFieldId " +
	                     "AND b.timeStart = :timeStart AND b.day = :day AND b.statusField = 'booked'";
	        Query query = session.createQuery(sql);
	        query.setParameter("smallFieldId", smallFieldId);
	        query.setParameter("timeStart", timeStart);
	        query.setParameter("day", day);
	        
	        Long count = (Long) query.getSingleResult();
	        exists = count > 0; // Nếu có bản ghi, trả về true
	        System.out.println("Booking check result for smallFieldId=" + smallFieldId + ", timeStart=" + timeStart + ", day=" + day + ": " + exists);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return exists;
	}




	
}
