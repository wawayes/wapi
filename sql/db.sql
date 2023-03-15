use wapi;
-- 接口信息
create table if not exists wapi.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '接口名字',
    `url` varchar(256) not null comment '地址',
    `description` varchar(256) not null comment '描述',
    `status` int default 0 not null comment '状态 0 - 关闭 1 - 开启',
    `requestParams` text null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `method` varchar(256) not null comment '接口类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

create table if not exists wapi.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户Id',
    `interfaceInfoId` bigint not null comment '接口Id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常 ，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'

) comment '用户调用接口关系表';


insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('林志强', 'www.yolando-hessel.info', '王伟宸', 0, '龙语堂', '邱聪健', '黎雪松', 94);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('邹明杰', 'www.juan-hirthe.info', '石晋鹏', 0, '林烨华', '彭峻熙', '杜雨泽', 8);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('徐聪健', 'www.haywood-simonis.org', '尹天翊', 0, '石熠彤', '戴昊强', '毛驰', 261479);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('薛鹤轩', 'www.creola-howe.net', '许金鑫', 0, '林凯瑞', '马凯瑞', '胡瑾瑜', 157);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('黄鹏涛', 'www.darin-mosciski.co', '段鸿煊', 0, '沈瑾瑜', '秦鹭洋', '阎子轩', 69);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('陆懿轩', 'www.marshall-altenwerth.info', '杜鸿煊', 0, '韦弘文', '谭风华', '黎正豪', 40);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('于明辉', 'www.cristy-moore.info', '汪鹭洋', 0, '戴鸿煊', '史明杰', '黄文轩', 8951282285);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('郭鸿煊', 'www.gordon-schinner.io', '韦炫明', 0, '于熠彤', '袁晓啸', '王峻熙', 2880175186);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('郝思', 'www.haley-johnson.biz', '阎煜祺', 0, '潘鹭洋', '段天翊', '傅瑾瑜', 358269877);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('尹晋鹏', 'www.domenica-grady.biz', '石思聪', 0, '廖峻熙', '曾鹏', '秦峻熙', 71);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('刘昊然', 'www.tad-brown.info', '武靖琪', 0, '任正豪', '毛思源', '韦天宇', 225900);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('万俊驰', 'www.will-fadel.biz', '邓绍齐', 0, '蔡烨磊', '赖智辉', '韩文', 753055104);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('钟睿渊', 'www.donn-kovacek.io', '谭子默', 0, '吴思聪', '尹晋鹏', '周耀杰', 50840);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('孔弘文', 'www.renato-ullrich.name', '李鹏涛', 0, '杨瑞霖', '任楷瑞', '孟熠彤', 2);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('彭振家', 'www.todd-satterfield.biz', '苏煜祺', 0, '石明', '秦思', '杜思', 72);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('李俊驰', 'www.laureen-heathcote.info', '赵弘文', 0, '罗雨泽', '孙伟宸', '刘伟诚', 5974477858);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('陶烨磊', 'www.wilson-mante.net', '石瑞霖', 0, '金彬', '崔锦程', '严明', 154239619);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('夏雪松', 'www.gabriel-fisher.org', '江擎苍', 0, '冯涛', '方嘉懿', '雷志泽', 4704127);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('黄钰轩', 'www.andreas-gerhold.co', '邵烨霖', 0, '钟雪松', '陆明辉', '彭志泽', 7374622);
insert into wapi.`interface_info` (`name`, `url`, `description`, `status`, `requestHeader`, `responseHeader`, `method`, `userId`) values ('刘子骞', 'www.michael-sporer.io', '郝彬', 0, '潘峻熙', '罗鑫鹏', '方烨磊', 8104363373);