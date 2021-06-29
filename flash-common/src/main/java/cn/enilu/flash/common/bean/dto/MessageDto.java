package cn.enilu.flash.common.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 消息发送对象
 *
 * @Author enilu
 * @Date 2021/6/29 17:39
 * @Version 1.0
 */
@Data
public class MessageDto implements Serializable {
    private String tplCode;
    private String to;
    private String cc;
    private String title;
    private String content;
    private LinkedHashMap dataParams;
    public MessageDto(){

    }
    public MessageDto(String tplCode,String to,LinkedHashMap dataParams){
        this.tplCode = tplCode;
        this.to = to;
        this.dataParams = dataParams;
    }
    public static MessageDto buildSmsDto(String tplCode,String mobile,LinkedHashMap dataParams){
        return new MessageDto(tplCode,mobile,dataParams);
    }
    public static MessageDto buildSimpleEmailDto(String tplCode,String to,String cc,String title,String content ){
        MessageDto dto = new MessageDto( );
        dto.setTplCode(tplCode);
        dto.setTo(to);
        dto.setCc(cc);
        dto.setTitle(title);
        dto.setContent(content);
        return dto;
    }
    public static MessageDto buildTplEamilDto(String tplCode,String to,String cc,String title,LinkedHashMap dataParams){
         MessageDto dto = new MessageDto( );
         dto.setTplCode(tplCode);
         dto.setTo(to);
         dto.setCc(cc);
         dto.setTitle(title);
         dto.setDataParams(dataParams);
         return dto;
    }
}
