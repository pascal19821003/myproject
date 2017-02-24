/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package sinc.tests.json;

public class NetcoolWarn {
	private String handle;	//Netcool告警ID
	private String count;	//次数
	private String first_occurrence;	//最早发生时间
	private String last_occurrence;	//最后发生时间
	private String node_ip;	//节点IP
	private String node_alias;	//节点别名
	private String severity;	//告警级别
	private String alert_group;	//报警组
	private String alert_class;	//线路类型
	private String alert_object;	//线路编号
	private String summary;	//摘要
	private String contact;	//联系人
	private String n_phone;	//电话
	private String n_net_area;	//网络区域
	private String n_bandwidth;	//带宽
	private String n_ispname;	//运营商
	private String n_organization;	//机构
	private Long timemillisstr;
	private String servername;
	private String source;
	private String area;
	public NetcoolWarn() {
		super();
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFirst_occurrence() {
		return first_occurrence;
	}
	public void setFirst_occurrence(String first_occurrence) {
		this.first_occurrence = first_occurrence;
	}
	public String getLast_occurrence() {
		return last_occurrence;
	}
	public void setLast_occurrence(String last_occurrence) {
		this.last_occurrence = last_occurrence;
	}
	public String getNode_ip() {
		return node_ip;
	}
	public void setNode_ip(String node_ip) {
		this.node_ip = node_ip;
	}
	public String getNode_alias() {
		return node_alias;
	}
	public void setNode_alias(String node_alias) {
		this.node_alias = node_alias;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getAlert_group() {
		return alert_group;
	}
	public void setAlert_group(String alert_group) {
		this.alert_group = alert_group;
	}
	public String getAlert_class() {
		return alert_class;
	}
	public void setAlert_class(String alert_class) {
		this.alert_class = alert_class;
	}
	public String getAlert_object() {
		return alert_object;
	}
	public void setAlert_object(String alert_object) {
		this.alert_object = alert_object;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getN_phone() {
		return n_phone;
	}
	public void setN_phone(String n_phone) {
		this.n_phone = n_phone;
	}
	public String getN_net_area() {
		return n_net_area;
	}
	public void setN_net_area(String n_net_area) {
		this.n_net_area = n_net_area;
	}
	public String getN_bandwidth() {
		return n_bandwidth;
	}
	public void setN_bandwidth(String n_bandwidth) {
		this.n_bandwidth = n_bandwidth;
	}
	public String getN_ispname() {
		return n_ispname;
	}
	public void setN_ispname(String n_ispname) {
		this.n_ispname = n_ispname;
	}
	public String getN_organization() {
		return n_organization;
	}
	public void setN_organization(String n_organization) {
		this.n_organization = n_organization;
	}
	public Long getTimemillisstr() {
		return timemillisstr;
	}
	public void setTimemillisstr(Long timemillisstr) {
		this.timemillisstr = timemillisstr;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
