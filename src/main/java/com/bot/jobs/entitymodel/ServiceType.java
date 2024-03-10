package com.bot.jobs.entitymodel;

import com.bot.jobs.db.annotations.Column;
import com.bot.jobs.db.annotations.Id;
import com.bot.jobs.db.annotations.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "ServiceType")
@NoArgsConstructor
@Data
public class ServiceType {
    @Id
    @Column(name = "ServiceTypeId")
    int serviceTypeId;
    @Column(name = "ServiceName")
    String serviceName;
    @Column(name = "ServiceDescription")
    String serviceDescription;
}
