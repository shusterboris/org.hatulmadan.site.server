package org.hatulmadan.site.server.application.data.proxies;

import java.time.ZonedDateTime;

import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.courses.Payments;

import lombok.Getter;
import lombok.Setter;

/**
 * @author innai
 *  клиенту отпрвляем имя группы
 *  от клиента получаем id группы
 */
@Getter
@Setter
public class PaymentProxy {
	private ZonedDateTime pdate;
	private Long userId;
	private Double psum;
	private String comment;
	private String groupName;
	
	public PaymentProxy(Payments entity,String gName){
		userId=entity.getId();
		 pdate=entity.getPdate();
		 psum=entity.getPsum();
		 comment=entity.getComment();
		 groupName=gName;
	}
	public Payments createPayment(){
		Payments p=new Payments();
		p.setComment(comment);
		p.setPdate(pdate);
		p.setPsum(psum);
		p.setUserId(userId);
		p.setGroupId(Long.valueOf(groupName));
		return p;
	}
}
