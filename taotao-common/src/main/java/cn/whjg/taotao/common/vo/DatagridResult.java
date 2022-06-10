package cn.whjg.taotao.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatagridResult<T> {
    private Long total;
    private List<T> rows;

}
