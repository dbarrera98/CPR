/*
 * Copyright (c) 2011 Cobiscorp (Banking Technology Partners) SA, All Rights Reserved.
 *
 * This code is confidential to Cobiscorp and shall not be disclosed outside Cobiscorp
 * without the prior written permission of the Technology Center.
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favor of Cobiscorp SA.
 *
 */
package com.cobiscorp.financial.trn.cac.pagaportaciones.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cobiscorp.cobis.commons.exceptions.COBISException;
import com.cobiscorp.cobis.commons.log.ILogger;
import com.cobiscorp.cobis.commons.log.LogFactory;
import com.cobiscorp.cobis.cwc.server.information.ServerParamUtil;
import com.cobiscorp.cobis.engine.services.Constants;
import com.cobiscorp.cobis.engine.services.FinantialBase;
import com.cobiscorp.cobis.finantial.engine.action.executor.dto.ParameterSP;
import com.cobiscorp.cobis.finantial.engine.action.executor.enums.ParameterType;
import com.cobiscorp.cobis.finantial.engine.action.executor.services.impl.IFinantialEngine;
import com.cobiscorp.cobis.finantial.engine.dto.Action;
import com.cobiscorp.cobis.finantial.engine.dto.Attribute;
import com.cobiscorp.cobis.finantial.engine.dto.Catalog;
import com.cobiscorp.cobis.finantial.engine.dto.Composite;
import com.cobiscorp.cobis.finantial.engine.dto.Composite.TypeButton;
import com.cobiscorp.cobis.finantial.engine.dto.Event;
import com.cobiscorp.cobis.finantial.engine.dto.Variable;
import com.cobiscorp.cobis.finantial.engine.enums.ActionType;
import com.cobiscorp.cobis.finantial.engine.enums.DataType;
import com.cobiscorp.ecobis.finantial.engine.utils.ConvertUtil;
import com.cobiscorp.ecobis.finantial.engine.utils.Parameter;
import com.cobiscorp.ecobis.map.enums.SqlType;

public abstract class PagoAportacionesCacGen extends FinantialBase implements IFinantialEngine {
	private static final ILogger LOGGER = LogFactory.getLogger(PagoAportacionesCacGen.class);

	/* Constantes para las etiquetas de los campos */
	public static final String TIT_TRANSACCION = "PAGAPORTACIONES.TIT_TRANSACCION";
	public static final String LBL_TXTNUMCUENTA = "PAGAPORTACIONES.LBL_TXTNUMCUENTA";
	public static final String LBL_TXTNOMBRE = "PAGAPORTACIONES.LBL_TXTNOMBRE";
	public static final String LBL_TXTMONEDA = "PAGAPORTACIONES.LBL_TXTMONEDA";
	public static final String LBL_TXTEFECTIVO = "PAGAPORTACIONES.LBL_TXTEFECTIVO";
	public static final String LBL_MKMONTORECIBIR = "PAGAPORTACIONES.LBL_MKMONTORECIBIR";
	public static final String LBL_MKTASA = "PAGAPORTACIONES.LBL_MKTASA";
	public static final String LBL_MKMONRECPROP = "PAGAPORTACIONES.LBL_MKMONRECPROP";
	public static final String LBL_TXTCHQPROPIOS = "PAGAPORTACIONES.LBL_TXTCHQPROPIOS";
	public static final String LBL_TXTCHQLOCAL = "PAGAPORTACIONES.LBL_TXTCHQLOCAL";
	public static final String LBL_MKMONRECLOC = "PAGAPORTACIONES.LBL_MKMONRECLOC";
	public static final String LBL_TXTCHQOTRASP = "PAGAPORTACIONES.LBL_TXTCHQOTRASP";
	public static final String LBL_TXTTOTDEPOSITO = "PAGAPORTACIONES.LBL_TXTTOTDEPOSITO";
	public static final String LBL_LBLTOTCONV = "PAGAPORTACIONES.LBL_LBLTOTCONV";
	public static final String LBL_TXTREFERENCIA = "PAGAPORTACIONES.LBL_TXTREFERENCIA";
	public static final String LBL_TXTSALCUENTA = "PAGAPORTACIONES.LBL_TXTSALCUENTA";
	public static final String LBL_TXTLETRAS = "PAGAPORTACIONES.LBL_TXTLETRAS";
	public static final String LBL_LBLPATENTE = "PAGAPORTACIONES.LBL_LBLPATENTE";
	public static final String LBL_TXTDOCPROPIOS = "PAGAPORTACIONES.LBL_TXTDOCPROPIOS";
	public static final String LBL_TXTLETRAS1 = "PAGAPORTACIONES.LBL_TXTLETRAS1";
	public static final String LBL_TXTCODOCACIONAL = "PAGAPORTACIONES.LBL_TXTCODOCACIONAL";
	public static final String LBL_TXTBOLETA = "PAGAPORTACIONES.LBL_TXTBOLETA";
	public static final String LBL_CONCEPTO = "PAGAPORTACIONES.LBL_CONCEPTO";
	protected static List<Attribute> templateAttributes;
	protected static List<Variable> templateVariables;
	protected static Map<String, Integer> attributesByName;
	protected static String transactionClassName;
	private static List<Attribute> tmpAttributes;
	private static List<Variable> tmpVariables;

	/* Constantes para los nombres de campos */
	public static final String FIELD_TXTNUMCUENTA = "TXTNUMCUENTA";
	public static final String FIELD_TXTNOMBRE = "TXTNOMBRE";
	public static final String FIELD_TXTMONEDA = "TXTMONEDA";
	public static final String FIELD_TXTEFECTIVO = "TXTEFECTIVO";
	public static final String FIELD_MKMONTORECIBIR = "MKMONTORECIBIR";
	public static final String FIELD_MKTASA = "MKTASA";
	public static final String FIELD_MKMONRECPROP = "MKMONRECPROP";
	public static final String FIELD_TXTCHQPROPIO = "TXTCHQPROPIO";
	public static final String FIELD_TXTCHQLOCAL = "TXTCHQLOCAL";
	public static final String FIELD_MKMONRECLOC = "MKMONRECLOC";
	public static final String FIELD_TXTCHQOTRASP = "TXTCHQOTRASP";
	public static final String FIELD_TXTTOTDEPOSITO = "TXTTOTDEPOSITO";
	public static final String FIELD_LBLTOTCONV = "LBLTOTCONV";
	public static final String FIELD_TXTREFERENCIA = "TXTREFERENCIA";
	public static final String FIELD_TXTSALCUENTA = "TXTSALCUENTA";
	public static final String FIELD_TXTLETRAS = "TXTLETRAS";
	public static final String FIELD_LBLPATENTE = "LBLPATENTE";
	public static final String FIELD_TXTDOCPROPIOS = "TXTDOCPROPIOS";
	public static final String FIELD_TXTLETRAS1 = "TXTLETRAS1";
	public static final String FIELD_TXTCODOCACIONAL = "TXTCODOCACIONAL";
	public static final String FIELD_TXTBOLETA = "TXTBOLETA";
	public static final String FIELD_CONCEPTO = "CONCEPTO";

	// Agrego atributos para pintar en la pagina
	static {
		tmpAttributes = new ArrayList<>();
		tmpVariables = new ArrayList<>();
		int index = 0;
		attributesByName = new HashMap<>();
		//

		Composite attribute_composite = new Composite(FIELD_TXTNUMCUENTA, DataType.COMPOSITE);
		attribute_composite.setLabel(LBL_TXTNUMCUENTA);
		attribute_composite.setTypeFirstComponent(DataType.MASK);
		attribute_composite.setTypeButton(TypeButton.SEARCH);
		//attribute_composite.setMask(getFormatMask("4"));
		attribute_composite.setParameterName("@i_cta");
		attribute_composite.getAttributesAssociate().put(FIELD_TXTNOMBRE, null);
		tmpAttributes.add(attribute_composite);
		attributesByName.put(FIELD_TXTNUMCUENTA, index++);
		//
		Attribute attribute_txtnombre = new Attribute(FIELD_TXTNOMBRE, DataType.STRING);
		attribute_txtnombre.setEnabled(Boolean.FALSE);
		attribute_txtnombre.setLabel(LBL_TXTNOMBRE);
		attribute_txtnombre.setParameterName("@i_nombre");
		tmpAttributes.add(attribute_txtnombre);
		attributesByName.put(FIELD_TXTNOMBRE, index++);
		//
		Attribute attribute_txtefectivo = new Attribute(FIELD_TXTEFECTIVO, DataType.CURRENCY);
		attribute_txtefectivo.setLabel(LBL_TXTEFECTIVO);
		attribute_txtefectivo.setParameterName("@i_efe");
		attribute_txtefectivo.setRequired(Boolean.FALSE);
		attribute_txtefectivo.setValueAttribute(BigDecimal.ZERO);
		attribute_txtefectivo.setSumadora(Boolean.TRUE);
		tmpAttributes.add(attribute_txtefectivo);
		attributesByName.put(FIELD_TXTEFECTIVO, index++);
		//
		Attribute attribute_txtchqpropio = new Attribute(FIELD_TXTCHQPROPIO, DataType.CURRENCY);
		attribute_txtchqpropio.setLabel(LBL_TXTCHQPROPIOS);
		attribute_txtchqpropio.setParameterName("@i_prop");
		attribute_txtchqpropio.setRequired(Boolean.FALSE);
		attribute_txtchqpropio.setValueAttribute(BigDecimal.ZERO);
		attribute_txtchqpropio.setCheckDetail(Boolean.TRUE);
		//attribute_txtchqpropio.setReadOnly(Boolean.FALSE);
		tmpAttributes.add(attribute_txtchqpropio);
		attributesByName.put(FIELD_TXTCHQPROPIO, index++);
		//
		Attribute attribute_txtchqlocal = new Attribute(FIELD_TXTCHQLOCAL, DataType.CURRENCY);
		attribute_txtchqlocal.setLabel(LBL_TXTCHQLOCAL);
		attribute_txtchqlocal.setParameterName("@i_loc");
		attribute_txtchqlocal.setRequired(Boolean.FALSE);
		attribute_txtchqlocal.setValueAttribute(BigDecimal.ZERO);
		attribute_txtchqlocal.setCheckDetail(Boolean.TRUE);
		tmpAttributes.add(attribute_txtchqlocal);
		attributesByName.put(FIELD_TXTCHQLOCAL, index++);
		//
		Attribute attribute_txtchqotrasp = new Attribute(FIELD_TXTCHQOTRASP, DataType.CURRENCY);
		attribute_txtchqotrasp.setLabel(LBL_TXTCHQOTRASP);
		attribute_txtchqotrasp.setVisible(Boolean.FALSE);
		attribute_txtchqotrasp.setParameterName("@i_plaz");
		attribute_txtchqotrasp.setRequired(Boolean.FALSE);
		attribute_txtchqotrasp.setValueAttribute(BigDecimal.ZERO);
		attribute_txtchqotrasp.setCheckDetail(Boolean.FALSE);
		tmpAttributes.add(attribute_txtchqotrasp);
		attributesByName.put(FIELD_TXTCHQOTRASP, index++);
		//
		Attribute attribute_txttotdeposito = new Attribute(FIELD_TXTTOTDEPOSITO, DataType.CURRENCY);
		attribute_txttotdeposito.setEnabled(Boolean.FALSE);
		attribute_txttotdeposito.setLabel(LBL_TXTTOTDEPOSITO);
		attribute_txttotdeposito.setMaxLength(null);
		attribute_txttotdeposito.setMaxValue(null);
		attribute_txttotdeposito.setParameterName("@i_total");
		attribute_txttotdeposito.setValueAttribute(BigDecimal.ZERO);
		tmpAttributes.add(attribute_txttotdeposito);
		attributesByName.put(FIELD_TXTTOTDEPOSITO, index++);
		//
		Attribute attribute_concepto = new Attribute(FIELD_CONCEPTO, DataType.STRING);
		attribute_concepto.setLabel(LBL_CONCEPTO);
		attribute_concepto.setMaxLength(30);
		attribute_concepto.setParameterName("@i_concep");
		attribute_concepto.setRequired(Boolean.FALSE);
		attribute_concepto.setVisible(Boolean.FALSE);
		tmpAttributes.add(attribute_concepto);
		attributesByName.put(FIELD_CONCEPTO, index++);

		// VARIABLES

		Variable variable;
		variable = new Variable("AUTORIZA");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("SSNBRANCH");
		variable.setParameterName("@o_ssn_branch");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLSSN");
		variable.setParameterName("@o_ssn");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLOFICINA");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLOFICINA1");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.SMALL_INT);
		tmpVariables.add(variable);

		variable = new Variable("VLPROD");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLCODCAJA");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLCOM");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLCANCELAR");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('N');
		tmpVariables.add(variable);

		variable = new Variable("ESTADO");
		variable.setParameterName("@i_estado");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("OPATENTE");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLCONSULTANOMBRE");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLFORMULARIO");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VLCODCLIENTE");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLTIPOTRAN");
		variable.setParameterName("@i_signo");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('I');
		tmpVariables.add(variable);

		variable = new Variable("VLNUMOPERACION");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		variable.setValue(252L);
		tmpVariables.add(variable);

		variable = new Variable("VLFORMULARIOCC");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VLMONTOCASUAL");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CURRENCY);
		tmpVariables.add(variable);

		variable = new Variable("VLTIPOCAMBIO");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CURRENCY);
		tmpVariables.add(variable);

		variable = new Variable("VLMONTOCONV");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CURRENCY);
		tmpVariables.add(variable);

		variable = new Variable("VLCODENTE");
		variable.setParameterName("@i_cod_ente");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLAPLICAROE");
		variable.setParameterName("@i_aplica_roe");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VLRETORNO");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		variable.setValue("True");
		tmpVariables.add(variable);

		variable = new Variable("VLGESTOR");
		variable.setParameterName("@i_gestor");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("CONST_MODULO");
		variable.setParameterName("@i_modulo");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		variable.setValue("AHO");
		tmpVariables.add(variable);

		variable = new Variable("CONST_OPERACION_C");
		variable.setParameterName("@i_operacion");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('C');
		tmpVariables.add(variable);

		variable = new Variable("FACTOR");
		variable.setParameterName("@o_factor");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CURRENCY);
		tmpVariables.add(variable);

		variable = new Variable("REVERSO");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('N');
		tmpVariables.add(variable);

		variable = new Variable("VLCOTUSD");
		variable.setParameterName("@o_cot_usd");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CURRENCY);
		tmpVariables.add(variable);

		variable = new Variable("CONST_TRAN_DEP");
		variable.setParameterName("@i_transaccion");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		variable.setValue("DEP");
		tmpVariables.add(variable);

		variable = new Variable("VLTIPOCHQ");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('0');
		tmpVariables.add(variable);

		variable = new Variable("VLDETCHQ");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('N');
		tmpVariables.add(variable);

		variable = new Variable("VLNUMCTRL");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		variable.setValue(0L);
		tmpVariables.add(variable);

		variable = new Variable("VLMSG");
		variable.setParameterName("@i_leyenda");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLTIPOID");
		variable.setParameterName("@i_tipo_id_cc");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLCEDULA");
		variable.setParameterName("@i_id_ce");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLNOMBRE");
		variable.setParameterName("@i_nombre_cc");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLDIRECCION");
		variable.setParameterName("@i_direccion_cc");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLTELEFONO");
		variable.setParameterName("@i_telefono_cc");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLORIGEN");
		variable.setParameterName("@i_origen");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLDESTINO");
		variable.setParameterName("@i_destino");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLMOTIVO");
		variable.setParameterName("@i_motivo");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLCODTITULAR");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLCODOCACIONAL");
		variable.setParameterName("@i_ente_ce");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		// TODO: para que genere modo reverso mientras se acple lavado de dinero
		variable.setValue(6L);
		tmpVariables.add(variable);

		variable = new Variable("VLGENERAFORM");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VLTIPOGENERA");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VLGENERO_FAC");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('N');
		tmpVariables.add(variable);

		variable = new Variable("VLSEC_FACT");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLMSGFAC");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		variable.setValue("ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS. EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LA LEY");
		tmpVariables.add(variable);

		variable = new Variable("VL_NIT");
		variable.setParameterName("@o_nit_bco");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VL_DES_OFI");
		variable.setParameterName("@o_des_ofi");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VL_COPIA_COMP");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('N');
		tmpVariables.add(variable);

		variable = new Variable("VL_TIPO_IMP_COMP");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('N');
		tmpVariables.add(variable);

		variable = new Variable("VLCENATG");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		variable.setValue('S');
		tmpVariables.add(variable);

		variable = new Variable("VL_FORMA_OP");
		variable.setParameterName("@o_forma_oper");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VLGENFACCV");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VLSECFACCV");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

		variable = new Variable("VLTIPOFAC");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CHAR);
		tmpVariables.add(variable);

		variable = new Variable("VL_TOT_DESC");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VL_NEM_BOL");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("VL_NEM_DOL");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("FORMULARIO");
		variable.setParameterName("@o_formulario");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("MONTO_FORMULARIO");
		variable.setParameterName("@o_monto_formulario");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.CURRENCY);
		tmpVariables.add(variable);

		variable = new Variable("VLEXISTECE");
		variable.setParameterName("@i_existe_ce");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable("OALERCARTERA");
		variable.setParameterName("@o_alerta_cartera");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.STRING);
		tmpVariables.add(variable);

		variable = new Variable(Constants.RETURN_CONTROL_CHECK);
		variable.setParameterName("@i_ctrlchq");
		variable.setGlobal(Boolean.TRUE);
		variable.setDataType(DataType.INT);
		tmpVariables.add(variable);

	}

	// Fin atributos para pintar en la pagina
	/** Metodo para agregar un nuevo atributo */
	protected static void addNewAttribute(Attribute attribute, int index) {
		tmpAttributes.add(index, attribute);
		attributesByName.put(attribute.getName(), index);
	}

	/**
	 * Default constructor
	 */
	public PagoAportacionesCacGen(String className) {
		transactionClassName = className;
		templateAttributes = Collections.unmodifiableList(tmpAttributes);
		templateVariables = Collections.unmodifiableList(tmpVariables);
	}

	/**
	 * Method for obtain static attributes
	 *
	 * @return List of original attributes
	 */
	public List<Attribute> getTemplateAttributes() {
		return templateAttributes;
	}

	/**
	 * Method for obtain original variables
	 *
	 * @return List of original variables
	 */
	public List<Variable> getTemplateVariables() {
		return templateVariables;
	}

	/* inicio metodos accesores */
	/**
	 * Method for obtain TXTNUMCUENTA
	 *
	 * @return Value for TXTNUMCUENTA
	 */
	public String getTXTNUMCUENTA() {
		if (!getContext().getReverseMode()) {
			Attribute aux = getAttribute(FIELD_TXTNUMCUENTA);
			return (String) aux.getValueAttribute();
		} else {
			return (String) getFieldValue(FIELD_TXTNUMCUENTA);
		}
	}

	/**
	 * Method for asign value in pTXTNUMCUENTA
	 */
	public void setTXTNUMCUENTA(String pTXTNUMCUENTA) {
		setFieldValue(FIELD_TXTNUMCUENTA, pTXTNUMCUENTA);
	}

	/**
	 * Method for obtain TXTNOMBRE
	 *
	 * @return Value for TXTNOMBRE
	 */
	public String getTXTNOMBRE() {
		return (String) getFieldValue(FIELD_TXTNOMBRE);
	}

	/**
	 * Method for asign value in pTXTNOMBRE
	 */
	public void setTXTNOMBRE(String pTXTNOMBRE) {
		setFieldValue(FIELD_TXTNOMBRE, pTXTNOMBRE);
	}

	/**
	 * Method for obtain TXTMONEDA
	 *
	 * @return Value for TXTMONEDA
	 */
	public Catalog getTXTMONEDA() {
		return (Catalog) getFieldValue(FIELD_TXTMONEDA);
	}

	/**
	 * Method for asign value in pTXTMONEDA
	 */
	public void setTXTMONEDA(Catalog pTXTMONEDA) {
		setFieldValue(FIELD_TXTMONEDA, pTXTMONEDA);
	}

	/**
	 * Method for obtain TXTEFECTIVO
	 *
	 * @return Value for TXTEFECTIVO
	 */
	public BigDecimal getTXTEFECTIVO() {
		return (BigDecimal) getFieldValue(FIELD_TXTEFECTIVO);
	}

	/**
	 * Method for asign value in pTXTEFECTIVO
	 */
	public void setTXTEFECTIVO(BigDecimal pTXTEFECTIVO) {
		setFieldValue(FIELD_TXTEFECTIVO, pTXTEFECTIVO);
	}

	/**
	 * Method for obtain MKMONTORECIBIR
	 *
	 * @return Value for MKMONTORECIBIR
	 */
	public BigDecimal getMKMONTORECIBIR() {
		return (BigDecimal) getFieldValue(FIELD_MKMONTORECIBIR);
	}

	/**
	 * Method for asign value in pMKMONTORECIBIR
	 */
	public void setMKMONTORECIBIR(BigDecimal pMKMONTORECIBIR) {
		setFieldValue(FIELD_MKMONTORECIBIR, pMKMONTORECIBIR);
	}

	/**
	 * Method for obtain MKTASA
	 *
	 * @return Value for MKTASA
	 */
	public BigDecimal getMKTASA() {
		return (BigDecimal) getFieldValue(FIELD_MKTASA);
	}

	/**
	 * Method for asign value in pMKTASA
	 */
	public void setMKTASA(BigDecimal pMKTASA) {
		setFieldValue(FIELD_MKTASA, pMKTASA);
	}

	/**
	 * Method for obtain MKMONRECPROP
	 *
	 * @return Value for MKMONRECPROP
	 */
	public BigDecimal getMKMONRECPROP() {
		return (BigDecimal) getFieldValue(FIELD_MKMONRECPROP);
	}

	/**
	 * Method for asign value in pMKMONRECPROP
	 */
	public void setMKMONRECPROP(BigDecimal pMKMONRECPROP) {
		setFieldValue(FIELD_MKMONRECPROP, pMKMONRECPROP);
	}

	/**
	 * Method for obtain TXTCHQPROPIO
	 *
	 * @return Value for TXTCHQPROPIO
	 */
	public BigDecimal getTXTCHQPROPIO() {
		return (BigDecimal) getFieldValue(FIELD_TXTCHQPROPIO);
	}

	/**
	 * Method for asign value in pTXTCHQPROPIO
	 */
	public void setTXTCHQPROPIO(BigDecimal pTXTCHQPROPIO) {
		setFieldValue(FIELD_TXTCHQPROPIO, pTXTCHQPROPIO);
	}

	/**
	 * Method for obtain TXTCHQLOCAL
	 *
	 * @return Value for TXTCHQLOCAL
	 */
	public BigDecimal getTXTCHQLOCAL() {
		return (BigDecimal) getFieldValue(FIELD_TXTCHQLOCAL);
	}

	/**
	 * Method for asign value in pTXTCHQLOCAL
	 */
	public void setTXTCHQLOCAL(BigDecimal pTXTCHQLOCAL) {
		setFieldValue(FIELD_TXTCHQLOCAL, pTXTCHQLOCAL);
	}

	/**
	 * Method for obtain MKMONRECLOC
	 *
	 * @return Value for MKMONRECLOC
	 */
	public BigDecimal getMKMONRECLOC() {
		return (BigDecimal) getFieldValue(FIELD_MKMONRECLOC);
	}

	/**
	 * Method for asign value in pMKMONRECLOC
	 */
	public void setMKMONRECLOC(BigDecimal pMKMONRECLOC) {
		setFieldValue(FIELD_MKMONRECLOC, pMKMONRECLOC);
	}

	/**
	 * Method for obtain TXTCHQOTRASP
	 *
	 * @return Value for TXTCHQOTRASP
	 */
	public BigDecimal getTXTCHQOTRASP() {
		return (BigDecimal) getFieldValue(FIELD_TXTCHQOTRASP);
	}

	/**
	 * Method for asign value in pTXTCHQOTRASP
	 */
	public void setTXTCHQOTRASP(BigDecimal pTXTCHQOTRASP) {
		setFieldValue(FIELD_TXTCHQOTRASP, pTXTCHQOTRASP);
	}

	/**
	 * Method for obtain TXTTOTDEPOSITO
	 *
	 * @return Value for TXTTOTDEPOSITO
	 */
	public BigDecimal getTXTTOTDEPOSITO() {
		return (BigDecimal) getFieldValue(FIELD_TXTTOTDEPOSITO);
	}

	/**
	 * Method for asign value in pTXTTOTDEPOSITO
	 */
	public void setTXTTOTDEPOSITO(BigDecimal pTXTTOTDEPOSITO) {
		setFieldValue(FIELD_TXTTOTDEPOSITO, pTXTTOTDEPOSITO);
	}

	/**
	 * Method for obtain LBLTOTCONV
	 *
	 * @return Value for LBLTOTCONV
	 */
	public BigDecimal getLBLTOTCONV() {
		return (BigDecimal) getFieldValue(FIELD_LBLTOTCONV);
	}

	/**
	 * Method for asign value in pLBLTOTCONV
	 */
	public void setLBLTOTCONV(BigDecimal pLBLTOTCONV) {
		setFieldValue(FIELD_LBLTOTCONV, pLBLTOTCONV);
	}

	/**
	 * Method for obtain TXTREFERENCIA
	 *
	 * @return Value for TXTREFERENCIA
	 */
	public String getTXTREFERENCIA() {
		return (String) getFieldValue(FIELD_TXTREFERENCIA);
	}

	/**
	 * Method for asign value in pTXTREFERENCIA
	 */
	public void setTXTREFERENCIA(String pTXTREFERENCIA) {
		setFieldValue(FIELD_TXTREFERENCIA, pTXTREFERENCIA);
	}

	/**
	 * Method for obtain TXTSALCUENTA
	 *
	 * @return Value for TXTSALCUENTA
	 */
	public BigDecimal getTXTSALCUENTA() {
		return (BigDecimal) getFieldValue(FIELD_TXTSALCUENTA);
	}

	/**
	 * Method for asign value in pTXTSALCUENTA
	 */
	public void setTXTSALCUENTA(BigDecimal pTXTSALCUENTA) {
		setFieldValue(FIELD_TXTSALCUENTA, pTXTSALCUENTA);
	}

	/**
	 * Method for obtain TXTLETRAS
	 *
	 * @return Value for TXTLETRAS
	 */
	public String getTXTLETRAS() {
		return (String) getFieldValue(FIELD_TXTLETRAS);
	}

	/**
	 * Method for asign value in pTXTLETRAS
	 */
	public void setTXTLETRAS(String pTXTLETRAS) {
		setFieldValue(FIELD_TXTLETRAS, pTXTLETRAS);
	}

	/**
	 * Method for obtain LBLPATENTE
	 *
	 * @return Value for LBLPATENTE
	 */
	public String getLBLPATENTE() {
		return (String) getFieldValue(FIELD_LBLPATENTE);
	}

	/**
	 * Method for asign value in pLBLPATENTE
	 */
	public void setLBLPATENTE(String pLBLPATENTE) {
		setFieldValue(FIELD_LBLPATENTE, pLBLPATENTE);
	}

	/**
	 * Method for obtain TXTDOCPROPIOS
	 *
	 * @return Value for TXTDOCPROPIOS
	 */
	public BigDecimal getTXTDOCPROPIOS() {
		return (BigDecimal) getFieldValue(FIELD_TXTDOCPROPIOS);
	}

	/**
	 * Method for asign value in pTXTDOCPROPIOS
	 */
	public void setTXTDOCPROPIOS(BigDecimal pTXTDOCPROPIOS) {
		setFieldValue(FIELD_TXTDOCPROPIOS, pTXTDOCPROPIOS);
	}

	/**
	 * Method for obtain TXTLETRAS1
	 *
	 * @return Value for TXTLETRAS1
	 */
	public String getTXTLETRAS1() {
		return (String) getFieldValue(FIELD_TXTLETRAS1);
	}

	/**
	 * Method for asign value in pTXTLETRAS1
	 */
	public void setTXTLETRAS1(String pTXTLETRAS1) {
		setFieldValue(FIELD_TXTLETRAS1, pTXTLETRAS1);
	}

	/**
	 * Method for obtain TXTCODOCACIONAL
	 *
	 * @return Value for TXTCODOCACIONAL
	 */
	public String getTXTCODOCACIONAL() {
		return (String) getFieldValue(FIELD_TXTCODOCACIONAL);
	}

	/**
	 * Method for asign value in pTXTCODOCACIONAL
	 */
	public void setTXTCODOCACIONAL(String pTXTCODOCACIONAL) {
		setFieldValue(FIELD_TXTCODOCACIONAL, pTXTCODOCACIONAL);
	}

	/**
	 * Method for obtain TXTBOLETA
	 *
	 * @return Value for TXTBOLETA
	 */
	public Long getTXTBOLETA() {
		return (Long) getFieldValue(FIELD_TXTBOLETA);
	}

	/**
	 * Method for asign value in pTXTBOLETA
	 */
	public void setTXTBOLETA(Long pTXTBOLETA) {
		setFieldValue(FIELD_TXTBOLETA, pTXTBOLETA);
	}
	/* fin metodos accesores */

	/**
	 * Method for obtain title of page
	 */
	@Override
	public String getTitlePage() {
		return TIT_TRANSACCION;
	}

	/* inicio variables */
	/**
	 * Method to get AUTORIZA
	 * 
	 * @return Value for AUTORIZA
	 */
	public String getVarAUTORIZA() {
		return (String) getTransactionVariableValue("AUTORIZA");
	}

	/**
	 * Method to asign value for AUTORIZA
	 */
	public void setVarAUTORIZA(Object pAUTORIZA) {
		setTransactionVariableValue("AUTORIZA", convertObjToString(pAUTORIZA), Boolean.TRUE);
	}

	/**
	 * Method to get VLSSN
	 * 
	 * @return Value for VLSSN
	 */
	public Long getVarVLSSN() {
		return (Long) getTransactionVariableValue("VLSSN");
	}

	/**
	 * Method to asign value for VLSSN
	 */
	public void setVarVLSSN(Object pVLSSN) {
		setTransactionVariableValue("VLSSN", convertObjToLong(pVLSSN), Boolean.TRUE);
	}

	/**
	 * Method to get VLOFICINA
	 * 
	 * @return Value for VLOFICINA
	 */
	public String getVarVLOFICINA() {
		return (String) getTransactionVariableValue("VLOFICINA");
	}

	/**
	 * Method to asign value for VLOFICINA
	 */
	public void setVarVLOFICINA(Object pVLOFICINA) {
		setTransactionVariableValue("VLOFICINA", convertObjToString(pVLOFICINA), Boolean.TRUE);
	}

	/**
	 * Method to get VLOFICINA1
	 * 
	 * @return Value for VLOFICINA1
	 */
	public Long getVarVLOFICINA1() {
		return (Long) getTransactionVariableValue("VLOFICINA1");
	}

	/**
	 * Method to asign value for VLOFICINA1
	 */
	public void setVarVLOFICINA1(Object pVLOFICINA1) {
		setTransactionVariableValue("VLOFICINA1", convertObjToLong(pVLOFICINA1), Boolean.TRUE);
	}

	/**
	 * Method to get VLPROD
	 * 
	 * @return Value for VLPROD
	 */
	public String getVarVLPROD() {
		return (String) getTransactionVariableValue("VLPROD");
	}

	/**
	 * Method to asign value for VLPROD
	 */
	public void setVarVLPROD(Object pVLPROD) {
		setTransactionVariableValue("VLPROD", convertObjToString(pVLPROD), Boolean.TRUE);
	}

	/**
	 * Method to get VLCODCAJA
	 * 
	 * @return Value for VLCODCAJA
	 */
	public Long getVarVLCODCAJA() {
		return (Long) getTransactionVariableValue("VLCODCAJA");
	}

	/**
	 * Method to asign value for VLCODCAJA
	 */
	public void setVarVLCODCAJA(Object pVLCODCAJA) {
		setTransactionVariableValue("VLCODCAJA", convertObjToLong(pVLCODCAJA), Boolean.TRUE);
	}

	/**
	 * Method to get VLCOM
	 * 
	 * @return Value for VLCOM
	 */
	public String getVarVLCOM() {
		return (String) getTransactionVariableValue("VLCOM");
	}

	/**
	 * Method to asign value for VLCOM
	 */
	public void setVarVLCOM(Object pVLCOM) {
		setTransactionVariableValue("VLCOM", convertObjToString(pVLCOM), Boolean.TRUE);
	}

	/**
	 * Method to get VLCANCELAR
	 * 
	 * @return Value for VLCANCELAR
	 */
	public Character getVarVLCANCELAR() {
		return (Character) getTransactionVariableValue("VLCANCELAR");
	}

	/**
	 * Method to asign value for VLCANCELAR
	 */
	public void setVarVLCANCELAR(Object pVLCANCELAR) {
		setTransactionVariableValue("VLCANCELAR", convertObjToCharacter(pVLCANCELAR), Boolean.TRUE);
	}

	/**
	 * Method to get ESTADO
	 * 
	 * @return Value for ESTADO
	 */
	public Long getVarESTADO() {
		return (Long) getTransactionVariableValue("ESTADO");
	}

	/**
	 * Method to asign value for ESTADO
	 */
	public void setVarESTADO(Object pESTADO) {
		setTransactionVariableValue("ESTADO", convertObjToLong(pESTADO), Boolean.TRUE);
	}

	/**
	 * Method to get OPATENTE
	 * 
	 * @return Value for OPATENTE
	 */
	public String getVarOPATENTE() {
		return (String) getTransactionVariableValue("OPATENTE");
	}

	/**
	 * Method to asign value for OPATENTE
	 */
	public void setVarOPATENTE(Object pOPATENTE) {
		setTransactionVariableValue("OPATENTE", convertObjToString(pOPATENTE), Boolean.TRUE);
	}

	/**
	 * Method to get VLCONSULTANOMBRE
	 * 
	 * @return Value for VLCONSULTANOMBRE
	 */
	public String getVarVLCONSULTANOMBRE() {
		return (String) getTransactionVariableValue("VLCONSULTANOMBRE");
	}

	/**
	 * Method to asign value for VLCONSULTANOMBRE
	 */
	public void setVarVLCONSULTANOMBRE(Object pVLCONSULTANOMBRE) {
		setTransactionVariableValue("VLCONSULTANOMBRE", convertObjToString(pVLCONSULTANOMBRE), Boolean.TRUE);
	}

	/**
	 * Method to get VLFORMULARIO
	 * 
	 * @return Value for VLFORMULARIO
	 */
	public Character getVarVLFORMULARIO() {
		return (Character) getTransactionVariableValue("VLFORMULARIO");
	}

	/**
	 * Method to asign value for VLFORMULARIO
	 */
	public void setVarVLFORMULARIO(Object pVLFORMULARIO) {
		setTransactionVariableValue("VLFORMULARIO", convertObjToCharacter(pVLFORMULARIO), Boolean.TRUE);
	}

	/**
	 * Method to get VLCODCLIENTE
	 * 
	 * @return Value for VLCODCLIENTE
	 */
	public Long getVarVLCODCLIENTE() {
		return (Long) getTransactionVariableValue("VLCODCLIENTE");
	}

	/**
	 * Method to asign value for VLCODCLIENTE
	 */
	public void setVarVLCODCLIENTE(Object pVLCODCLIENTE) {
		setTransactionVariableValue("VLCODCLIENTE", convertObjToLong(pVLCODCLIENTE), Boolean.TRUE);
	}

	/**
	 * Method to get VLTIPOTRAN
	 * 
	 * @return Value for VLTIPOTRAN
	 */
	public Character getVarVLTIPOTRAN() {
		return (Character) getTransactionVariableValue("VLTIPOTRAN");
	}

	/**
	 * Method to asign value for VLTIPOTRAN
	 */
	public void setVarVLTIPOTRAN(Object pVLTIPOTRAN) {
		setTransactionVariableValue("VLTIPOTRAN", convertObjToCharacter(pVLTIPOTRAN), Boolean.TRUE);
	}

	/**
	 * Method to get VLNUMOPERACION
	 * 
	 * @return Value for VLNUMOPERACION
	 */
	public Long getVarVLNUMOPERACION() {
		return (Long) getTransactionVariableValue("VLNUMOPERACION");
	}

	/**
	 * Method to asign value for VLNUMOPERACION
	 */
	public void setVarVLNUMOPERACION(Object pVLNUMOPERACION) {
		setTransactionVariableValue("VLNUMOPERACION", convertObjToLong(pVLNUMOPERACION), Boolean.TRUE);
	}

	/**
	 * Method to get VLFORMULARIOCC
	 * 
	 * @return Value for VLFORMULARIOCC
	 */
	public Character getVarVLFORMULARIOCC() {
		return (Character) getTransactionVariableValue("VLFORMULARIOCC");
	}

	/**
	 * Method to asign value for VLFORMULARIOCC
	 */
	public void setVarVLFORMULARIOCC(Object pVLFORMULARIOCC) {
		setTransactionVariableValue("VLFORMULARIOCC", convertObjToCharacter(pVLFORMULARIOCC), Boolean.TRUE);
	}

	/**
	 * Method to get VLMONTOCASUAL
	 * 
	 * @return Value for VLMONTOCASUAL
	 */
	public BigDecimal getVarVLMONTOCASUAL() {
		return (BigDecimal) getTransactionVariableValue("VLMONTOCASUAL");
	}

	/**
	 * Method to asign value for VLMONTOCASUAL
	 */
	public void setVarVLMONTOCASUAL(Object pVLMONTOCASUAL) {
		setTransactionVariableValue("VLMONTOCASUAL", convertObjToBigDecimal(pVLMONTOCASUAL), Boolean.TRUE);
	}

	/**
	 * Method to get VLTIPOCAMBIO
	 * 
	 * @return Value for VLTIPOCAMBIO
	 */
	public BigDecimal getVarVLTIPOCAMBIO() {
		return (BigDecimal) getTransactionVariableValue("VLTIPOCAMBIO");
	}

	/**
	 * Method to asign value for VLTIPOCAMBIO
	 */
	public void setVarVLTIPOCAMBIO(Object pVLTIPOCAMBIO) {
		setTransactionVariableValue("VLTIPOCAMBIO", convertObjToBigDecimal(pVLTIPOCAMBIO), Boolean.TRUE);
	}

	/**
	 * Method to get VLMONTOCONV
	 * 
	 * @return Value for VLMONTOCONV
	 */
	public BigDecimal getVarVLMONTOCONV() {
		return (BigDecimal) getTransactionVariableValue("VLMONTOCONV");
	}

	/**
	 * Method to asign value for VLMONTOCONV
	 */
	public void setVarVLMONTOCONV(Object pVLMONTOCONV) {
		setTransactionVariableValue("VLMONTOCONV", convertObjToBigDecimal(pVLMONTOCONV), Boolean.TRUE);
	}

	/**
	 * Method to get VLCODENTE
	 * 
	 * @return Value for VLCODENTE
	 */
	public Long getVarVLCODENTE() {
		return (Long) getTransactionVariableValue("VLCODENTE");
	}

	/**
	 * Method to asign value for VLCODENTE
	 */
	public void setVarVLCODENTE(Object pVLCODENTE) {
		setTransactionVariableValue("VLCODENTE", convertObjToLong(pVLCODENTE), Boolean.TRUE);
	}

	/**
	 * Method to get VLAPLICAROE
	 * 
	 * @return Value for VLAPLICAROE
	 */
	public Character getVarVLAPLICAROE() {
		return (Character) getTransactionVariableValue("VLAPLICAROE");
	}

	/**
	 * Method to asign value for VLAPLICAROE
	 */
	public void setVarVLAPLICAROE(Object pVLAPLICAROE) {
		setTransactionVariableValue("VLAPLICAROE", convertObjToCharacter(pVLAPLICAROE), Boolean.TRUE);
	}

	/**
	 * Method to get VLRETORNO
	 * 
	 * @return Value for VLRETORNO
	 */
	public String getVarVLRETORNO() {
		return (String) getTransactionVariableValue("VLRETORNO");
	}

	/**
	 * Method to asign value for VLRETORNO
	 */
	public void setVarVLRETORNO(Object pVLRETORNO) {
		setTransactionVariableValue("VLRETORNO", convertObjToString(pVLRETORNO), Boolean.TRUE);
	}

	/**
	 * Method to get VLGESTOR
	 * 
	 * @return Value for VLGESTOR
	 */
	public Character getVarVLGESTOR() {
		return (Character) getTransactionVariableValue("VLGESTOR");
	}

	/**
	 * Method to asign value for VLGESTOR
	 */
	public void setVarVLGESTOR(Object pVLGESTOR) {
		setTransactionVariableValue("VLGESTOR", convertObjToCharacter(pVLGESTOR), Boolean.TRUE);
	}

	/**
	 * Method to get CONST_MODULO
	 * 
	 * @return Value for CONST_MODULO
	 */
	public String getVarCONST_MODULO() {
		return (String) getTransactionVariableValue("CONST_MODULO");
	}

	/**
	 * Method to asign value for CONST_MODULO
	 */
	public void setVarCONST_MODULO(Object pCONST_MODULO) {
		setTransactionVariableValue("CONST_MODULO", convertObjToString(pCONST_MODULO), Boolean.TRUE);
	}

	/**
	 * Method to get CONST_OPERACION_C
	 * 
	 * @return Value for CONST_OPERACION_C
	 */
	public Character getVarCONST_OPERACION_C() {
		return (Character) getTransactionVariableValue("CONST_OPERACION_C");
	}

	/**
	 * Method to asign value for CONST_OPERACION_C
	 */
	public void setVarCONST_OPERACION_C(Object pCONST_OPERACION_C) {
		setTransactionVariableValue("CONST_OPERACION_C", convertObjToCharacter(pCONST_OPERACION_C), Boolean.TRUE);
	}

	/**
	 * Method to get FACTOR
	 * 
	 * @return Value for FACTOR
	 */
	public BigDecimal getVarFACTOR() {
		return (BigDecimal) getTransactionVariableValue("FACTOR");
	}

	/**
	 * Method to asign value for FACTOR
	 */
	public void setVarFACTOR(Object pFACTOR) {
		setTransactionVariableValue("FACTOR", convertObjToBigDecimal(pFACTOR), Boolean.TRUE);
	}

	/**
	 * Method to get REVERSO
	 * 
	 * @return Value for REVERSO
	 */
	public Character getVarREVERSO() {
		return (Character) getTransactionVariableValue("REVERSO");
	}

	/**
	 * Method to asign value for REVERSO
	 */
	public void setVarREVERSO(Object pREVERSO) {
		setTransactionVariableValue("REVERSO", convertObjToCharacter(pREVERSO), Boolean.TRUE);
	}

	/**
	 * Method to get VLCOTUSD
	 * 
	 * @return Value for VLCOTUSD
	 */
	public BigDecimal getVarVLCOTUSD() {
		return (BigDecimal) getTransactionVariableValue("VLCOTUSD");
	}

	/**
	 * Method to asign value for VLCOTUSD
	 */
	public void setVarVLCOTUSD(Object pVLCOTUSD) {
		setTransactionVariableValue("VLCOTUSD", convertObjToBigDecimal(pVLCOTUSD), Boolean.TRUE);
	}

	/**
	 * Method to get CONST_TRAN_DEP
	 * 
	 * @return Value for CONST_TRAN_DEP
	 */
	public String getVarCONST_TRAN_DEP() {
		return (String) getTransactionVariableValue("CONST_TRAN_DEP");
	}

	/**
	 * Method to asign value for CONST_TRAN_DEP
	 */
	public void setVarCONST_TRAN_DEP(Object pCONST_TRAN_DEP) {
		setTransactionVariableValue("CONST_TRAN_DEP", convertObjToString(pCONST_TRAN_DEP), Boolean.TRUE);
	}

	/**
	 * Method to get VLTIPOCHQ
	 * 
	 * @return Value for VLTIPOCHQ
	 */
	public Character getVarVLTIPOCHQ() {
		return (Character) getTransactionVariableValue("VLTIPOCHQ");
	}

	/**
	 * Method to asign value for VLTIPOCHQ
	 */
	public void setVarVLTIPOCHQ(Object pVLTIPOCHQ) {
		setTransactionVariableValue("VLTIPOCHQ", convertObjToCharacter(pVLTIPOCHQ), Boolean.TRUE);
	}

	/**
	 * Method to get VLDETCHQ
	 * 
	 * @return Value for VLDETCHQ
	 */
	public Character getVarVLDETCHQ() {
		return (Character) getTransactionVariableValue("VLDETCHQ");
	}

	/**
	 * Method to asign value for VLDETCHQ
	 */
	public void setVarVLDETCHQ(Object pVLDETCHQ) {
		setTransactionVariableValue("VLDETCHQ", convertObjToCharacter(pVLDETCHQ), Boolean.TRUE);
	}

	/**
	 * Method to get VLNUMCTRL
	 * 
	 * @return Value for VLNUMCTRL
	 */
	public Long getVarVLNUMCTRL() {
		return (Long) getTransactionVariableValue("VLNUMCTRL");
	}

	/**
	 * Method to asign value for VLNUMCTRL
	 */
	public void setVarVLNUMCTRL(Object pVLNUMCTRL) {
		setTransactionVariableValue("VLNUMCTRL", convertObjToLong(pVLNUMCTRL), Boolean.TRUE);
	}

	/**
	 * Method to get VLMSG
	 * 
	 * @return Value for VLMSG
	 */
	public String getVarVLMSG() {
		return (String) getTransactionVariableValue("VLMSG");
	}

	/**
	 * Method to asign value for VLMSG
	 */
	public void setVarVLMSG(Object pVLMSG) {
		setTransactionVariableValue("VLMSG", convertObjToString(pVLMSG), Boolean.TRUE);
	}

	/**
	 * Method to get VLTIPOID
	 * 
	 * @return Value for VLTIPOID
	 */
	public String getVarVLTIPOID() {
		return (String) getTransactionVariableValue("VLTIPOID");
	}

	/**
	 * Method to asign value for VLTIPOID
	 */
	public void setVarVLTIPOID(Object pVLTIPOID) {
		setTransactionVariableValue("VLTIPOID", convertObjToString(pVLTIPOID), Boolean.TRUE);
	}

	/**
	 * Method to get VLCEDULA
	 * 
	 * @return Value for VLCEDULA
	 */
	public String getVarVLCEDULA() {
		return (String) getTransactionVariableValue("VLCEDULA");
	}

	/**
	 * Method to asign value for VLCEDULA
	 */
	public void setVarVLCEDULA(Object pVLCEDULA) {
		setTransactionVariableValue("VLCEDULA", convertObjToString(pVLCEDULA), Boolean.TRUE);
	}

	/**
	 * Method to get VLNOMBRE
	 * 
	 * @return Value for VLNOMBRE
	 */
	public String getVarVLNOMBRE() {
		return (String) getTransactionVariableValue("VLNOMBRE");
	}

	/**
	 * Method to asign value for VLNOMBRE
	 */
	public void setVarVLNOMBRE(Object pVLNOMBRE) {
		setTransactionVariableValue("VLNOMBRE", convertObjToString(pVLNOMBRE), Boolean.TRUE);
	}

	/**
	 * Method to get VLDIRECCION
	 * 
	 * @return Value for VLDIRECCION
	 */
	public String getVarVLDIRECCION() {
		return (String) getTransactionVariableValue("VLDIRECCION");
	}

	/**
	 * Method to asign value for VLDIRECCION
	 */
	public void setVarVLDIRECCION(Object pVLDIRECCION) {
		setTransactionVariableValue("VLDIRECCION", convertObjToString(pVLDIRECCION), Boolean.TRUE);
	}

	/**
	 * Method to get VLTELEFONO
	 * 
	 * @return Value for VLTELEFONO
	 */
	public String getVarVLTELEFONO() {
		return (String) getTransactionVariableValue("VLTELEFONO");
	}

	/**
	 * Method to asign value for VLTELEFONO
	 */
	public void setVarVLTELEFONO(Object pVLTELEFONO) {
		setTransactionVariableValue("VLTELEFONO", convertObjToString(pVLTELEFONO), Boolean.TRUE);
	}

	/**
	 * Method to get VLORIGEN
	 * 
	 * @return Value for VLORIGEN
	 */
	public String getVarVLORIGEN() {
		return (String) getTransactionVariableValue("VLORIGEN");
	}

	/**
	 * Method to asign value for VLORIGEN
	 */
	public void setVarVLORIGEN(Object pVLORIGEN) {
		setTransactionVariableValue("VLORIGEN", convertObjToString(pVLORIGEN), Boolean.TRUE);
	}

	/**
	 * Method to get VLDESTINO
	 * 
	 * @return Value for VLDESTINO
	 */
	public String getVarVLDESTINO() {
		return (String) getTransactionVariableValue("VLDESTINO");
	}

	/**
	 * Method to asign value for VLDESTINO
	 */
	public void setVarVLDESTINO(Object pVLDESTINO) {
		setTransactionVariableValue("VLDESTINO", convertObjToString(pVLDESTINO), Boolean.TRUE);
	}

	/**
	 * Method to get VLMOTIVO
	 * 
	 * @return Value for VLMOTIVO
	 */
	public String getVarVLMOTIVO() {
		return (String) getTransactionVariableValue("VLMOTIVO");
	}

	/**
	 * Method to asign value for VLMOTIVO
	 */
	public void setVarVLMOTIVO(Object pVLMOTIVO) {
		setTransactionVariableValue("VLMOTIVO", convertObjToString(pVLMOTIVO), Boolean.TRUE);
	}

	/**
	 * Method to get VLCODTITULAR
	 * 
	 * @return Value for VLCODTITULAR
	 */
	public Long getVarVLCODTITULAR() {
		return (Long) getTransactionVariableValue("VLCODTITULAR");
	}

	/**
	 * Method to asign value for VLCODTITULAR
	 */
	public void setVarVLCODTITULAR(Object pVLCODTITULAR) {
		setTransactionVariableValue("VLCODTITULAR", convertObjToLong(pVLCODTITULAR), Boolean.TRUE);
	}

	/**
	 * Method to get VLCODOCACIONAL
	 * 
	 * @return Value for VLCODOCACIONAL
	 */
	public Long getVarVLCODOCACIONAL() {
		return (Long) getTransactionVariableValue("VLCODOCACIONAL");
	}

	/**
	 * Method to asign value for VLCODOCACIONAL
	 */
	public void setVarVLCODOCACIONAL(Object pVLCODOCACIONAL) {
		setTransactionVariableValue("VLCODOCACIONAL", convertObjToLong(pVLCODOCACIONAL), Boolean.TRUE);
	}

	/**
	 * Method to get VLGENERAFORM
	 * 
	 * @return Value for VLGENERAFORM
	 */
	public Character getVarVLGENERAFORM() {
		return (Character) getTransactionVariableValue("VLGENERAFORM");
	}

	/**
	 * Method to asign value for VLGENERAFORM
	 */
	public void setVarVLGENERAFORM(Object pVLGENERAFORM) {
		setTransactionVariableValue("VLGENERAFORM", convertObjToCharacter(pVLGENERAFORM), Boolean.TRUE);
	}

	/**
	 * Method to get VLTIPOGENERA
	 * 
	 * @return Value for VLTIPOGENERA
	 */
	public Character getVarVLTIPOGENERA() {
		return (Character) getTransactionVariableValue("VLTIPOGENERA");
	}

	/**
	 * Method to asign value for VLTIPOGENERA
	 */
	public void setVarVLTIPOGENERA(Object pVLTIPOGENERA) {
		setTransactionVariableValue("VLTIPOGENERA", convertObjToCharacter(pVLTIPOGENERA), Boolean.TRUE);
	}

	/**
	 * Method to get VLGENERO_FAC
	 * 
	 * @return Value for VLGENERO_FAC
	 */
	public Character getVarVLGENERO_FAC() {
		return (Character) getTransactionVariableValue("VLGENERO_FAC");
	}

	/**
	 * Method to asign value for VLGENERO_FAC
	 */
	public void setVarVLGENERO_FAC(Object pVLGENERO_FAC) {
		setTransactionVariableValue("VLGENERO_FAC", convertObjToCharacter(pVLGENERO_FAC), Boolean.TRUE);
	}

	/**
	 * Method to get VLSEC_FACT
	 * 
	 * @return Value for VLSEC_FACT
	 */
	public Long getVarVLSEC_FACT() {
		return (Long) getTransactionVariableValue("VLSEC_FACT");
	}

	/**
	 * Method to asign value for VLSEC_FACT
	 */
	public void setVarVLSEC_FACT(Object pVLSEC_FACT) {
		setTransactionVariableValue("VLSEC_FACT", convertObjToLong(pVLSEC_FACT), Boolean.TRUE);
	}

	/**
	 * Method to get VLMSGFAC
	 * 
	 * @return Value for VLMSGFAC
	 */
	public String getVarVLMSGFAC() {
		return (String) getTransactionVariableValue("VLMSGFAC");
	}

	/**
	 * Method to asign value for VLMSGFAC
	 */
	public void setVarVLMSGFAC(Object pVLMSGFAC) {
		setTransactionVariableValue("VLMSGFAC", convertObjToString(pVLMSGFAC), Boolean.TRUE);
	}

	/**
	 * Method to get VL_NIT
	 * 
	 * @return Value for VL_NIT
	 */
	public String getVarVL_NIT() {
		return (String) getTransactionVariableValue("VL_NIT");
	}

	/**
	 * Method to asign value for VL_NIT
	 */
	public void setVarVL_NIT(Object pVL_NIT) {
		setTransactionVariableValue("VL_NIT", convertObjToString(pVL_NIT), Boolean.TRUE);
	}

	/**
	 * Method to get VL_DES_OFI
	 * 
	 * @return Value for VL_DES_OFI
	 */
	public String getVarVL_DES_OFI() {
		return (String) getTransactionVariableValue("VL_DES_OFI");
	}

	/**
	 * Method to asign value for VL_DES_OFI
	 */
	public void setVarVL_DES_OFI(Object pVL_DES_OFI) {
		setTransactionVariableValue("VL_DES_OFI", convertObjToString(pVL_DES_OFI), Boolean.TRUE);
	}

	/**
	 * Method to get VL_COPIA_COMP
	 * 
	 * @return Value for VL_COPIA_COMP
	 */
	public Character getVarVL_COPIA_COMP() {
		return (Character) getTransactionVariableValue("VL_COPIA_COMP");
	}

	/**
	 * Method to asign value for VL_COPIA_COMP
	 */
	public void setVarVL_COPIA_COMP(Object pVL_COPIA_COMP) {
		setTransactionVariableValue("VL_COPIA_COMP", convertObjToCharacter(pVL_COPIA_COMP), Boolean.TRUE);
	}

	/**
	 * Method to get VL_TIPO_IMP_COMP
	 * 
	 * @return Value for VL_TIPO_IMP_COMP
	 */
	public Character getVarVL_TIPO_IMP_COMP() {
		return (Character) getTransactionVariableValue("VL_TIPO_IMP_COMP");
	}

	/**
	 * Method to asign value for VL_TIPO_IMP_COMP
	 */
	public void setVarVL_TIPO_IMP_COMP(Object pVL_TIPO_IMP_COMP) {
		setTransactionVariableValue("VL_TIPO_IMP_COMP", convertObjToCharacter(pVL_TIPO_IMP_COMP), Boolean.TRUE);
	}

	/**
	 * Method to get VLCENATG
	 * 
	 * @return Value for VLCENATG
	 */
	public Character getVarVLCENATG() {
		return (Character) getTransactionVariableValue("VLCENATG");
	}

	/**
	 * Method to asign value for VLCENATG
	 */
	public void setVarVLCENATG(Object pVLCENATG) {
		setTransactionVariableValue("VLCENATG", convertObjToCharacter(pVLCENATG), Boolean.TRUE);
	}

	/**
	 * Method to get VL_FORMA_OP
	 * 
	 * @return Value for VL_FORMA_OP
	 */
	public String getVarVL_FORMA_OP() {
		return (String) getTransactionVariableValue("VL_FORMA_OP");
	}

	/**
	 * Method to asign value for VL_FORMA_OP
	 */
	public void setVarVL_FORMA_OP(Object pVL_FORMA_OP) {
		setTransactionVariableValue("VL_FORMA_OP", convertObjToString(pVL_FORMA_OP), Boolean.TRUE);
	}

	/**
	 * Method to get VLGENFACCV
	 * 
	 * @return Value for VLGENFACCV
	 */
	public Character getVarVLGENFACCV() {
		return (Character) getTransactionVariableValue("VLGENFACCV");
	}

	/**
	 * Method to asign value for VLGENFACCV
	 */
	public void setVarVLGENFACCV(Object pVLGENFACCV) {
		setTransactionVariableValue("VLGENFACCV", convertObjToCharacter(pVLGENFACCV), Boolean.TRUE);
	}

	/**
	 * Method to get VLSECFACCV
	 * 
	 * @return Value for VLSECFACCV
	 */
	public Long getVarVLSECFACCV() {
		return (Long) getTransactionVariableValue("VLSECFACCV");
	}

	/**
	 * Method to asign value for VLSECFACCV
	 */
	public void setVarVLSECFACCV(Object pVLSECFACCV) {
		setTransactionVariableValue("VLSECFACCV", convertObjToLong(pVLSECFACCV), Boolean.TRUE);
	}

	/**
	 * Method to get VLTIPOFAC
	 * 
	 * @return Value for VLTIPOFAC
	 */
	public Character getVarVLTIPOFAC() {
		return (Character) getTransactionVariableValue("VLTIPOFAC");
	}

	/**
	 * Method to asign value for VLTIPOFAC
	 */
	public void setVarVLTIPOFAC(Object pVLTIPOFAC) {
		setTransactionVariableValue("VLTIPOFAC", convertObjToCharacter(pVLTIPOFAC), Boolean.TRUE);
	}

	/**
	 * Method to get VL_TOT_DESC
	 * 
	 * @return Value for VL_TOT_DESC
	 */
	public String getVarVL_TOT_DESC() {
		return (String) getTransactionVariableValue("VL_TOT_DESC");
	}

	/**
	 * Method to asign value for VL_TOT_DESC
	 */
	public void setVarVL_TOT_DESC(Object pVL_TOT_DESC) {
		setTransactionVariableValue("VL_TOT_DESC", convertObjToString(pVL_TOT_DESC), Boolean.TRUE);
	}

	/**
	 * Method to get VL_NEM_BOL
	 * 
	 * @return Value for VL_NEM_BOL
	 */
	public String getVarVL_NEM_BOL() {
		return (String) getTransactionVariableValue("VL_NEM_BOL");
	}

	/**
	 * Method to asign value for VL_NEM_BOL
	 */
	public void setVarVL_NEM_BOL(Object pVL_NEM_BOL) {
		setTransactionVariableValue("VL_NEM_BOL", convertObjToString(pVL_NEM_BOL), Boolean.TRUE);
	}

	/**
	 * Method to get VL_NEM_DOL
	 * 
	 * @return Value for VL_NEM_DOL
	 */
	public String getVarVL_NEM_DOL() {
		return (String) getTransactionVariableValue("VL_NEM_DOL");
	}

	/**
	 * Method to asign value for VL_NEM_DOL
	 */
	public void setVarVL_NEM_DOL(Object pVL_NEM_DOL) {
		setTransactionVariableValue("VL_NEM_DOL", convertObjToString(pVL_NEM_DOL), Boolean.TRUE);
	}

	/**
	 * Method for obtain CONCEPTO
	 * 
	 * @return Value for CONCEPTO
	 */
	public String getCONCEPTO() {
		return (String) getFieldValue(FIELD_CONCEPTO);
	}

	/**
	 * Method for asign value in CONCEPTO
	 */
	public void setCONCEPTO(String CONCEPTO) {
		setFieldValue(FIELD_CONCEPTO, CONCEPTO);
	}

	/**
	 * Method to get FORMULARIO
	 * 
	 * @return Value for FORMULARIO
	 */
	public String getVarFORMULARIO() {
		String valueVar = (String) getTransactionVariableValue("FORMULARIO");
		if (valueVar == null) {
			valueVar = new String("");
		}
		return valueVar;
	}

	/**
	 * Method to asign value for ID_CIERRE
	 */
	public void setVarFORMULARIO(Object pFORMULARIO) {
		if (pFORMULARIO == null) {
			setTransactionVariableValue("FORMULARIO", pFORMULARIO);
		} else {
			setTransactionVariableValue("FORMULARIO", convertObjToString(pFORMULARIO));
		}
	}

	/**
	 * Method to get MONTO_FORMULARIO
	 * 
	 * @return Value for MONTO_FORMULARIO
	 */
	public BigDecimal getVarMONTO_FORMULARIO() {
		BigDecimal valueVar = (BigDecimal) getTransactionVariableValue("MONTO_FORMULARIO");
		if (valueVar == null) {
			valueVar = new BigDecimal("0");
		}
		return valueVar;
	}

	/**
	 * Method to asign value for MONTO_FORMULARIO
	 */
	public void setVarMONTO_FORMULARIO(Object pMONTO_FORMULARIO) {
		if (pMONTO_FORMULARIO == null) {
			setTransactionVariableValue("MONTO_FORMULARIO", pMONTO_FORMULARIO);
		} else {
			setTransactionVariableValue("MONTO_FORMULARIO", convertObjToBigDecimal(pMONTO_FORMULARIO));
		}
	}

	/**
	 * Method to get SALDO_CAJA
	 * 
	 * @return Value for SALDO_CAJA
	 */
	public BigDecimal getVarSALDO_CAJA() {
		BigDecimal valueVar = (BigDecimal) getTransactionVariableValue("SALDO_CAJA");
		if (valueVar == null) {
			valueVar = new BigDecimal("0");
		}
		return valueVar;
	}

	/**
	 * Method to asign value for SALDO_CAJA
	 */
	public void setVarSALDO_CAJA(Object pSALDO_CAJA) {
		setTransactionVariableValue("SALDO_CAJA", convertObjToBigDecimal(pSALDO_CAJA), Boolean.TRUE);
	}

	/**
	 * Method to get ID_CIERRE
	 * 
	 * @return Value for ID_CIERRE
	 */
	public int getVarID_CIERRE() {
		Integer valueVar = (Integer) getTransactionVariableValue("ID_CIERRE");
		if (valueVar == null) {
			valueVar = new Integer("0");
		}
		return valueVar;
	}

	/**
	 * Method to asign value for ID_CIERRE
	 */
	public void setVarID_CIERRE(Object pID_CIERRE) {
		if (pID_CIERRE == null) {
			setTransactionVariableValue("ID_CIERRE", pID_CIERRE);
		} else {
			setTransactionVariableValue("ID_CIERRE", convertObjToInteger(pID_CIERRE));
		}
	}

	/**
	 * Method to get SALDO_DISPONIBLE
	 * 
	 * @return Value for SALDO_DISPONIBLE
	 */
	public BigDecimal getVarSALDO_DISPONIBLE() {
		BigDecimal valueVar = (BigDecimal) getTransactionVariableValue("SALDO_DISPONIBLE");
		if (valueVar == null) {
			valueVar = new BigDecimal("0");
		}
		return valueVar;
	}

	/**
	 * Method to asign value for SALDO_DISPONIBLE
	 */
	public void setVarSALDO_DISPONIBLE(Object pSALDO_DISPONIBLE) {
		setTransactionVariableValue("SALDO_DISPONIBLE", convertObjToBigDecimal(pSALDO_DISPONIBLE), Boolean.TRUE);
	}

	/**
	 * Method to get SSNBRANCH
	 * 
	 * @return Value for SSNBRANCH
	 */
	public Long getVarSSNBRANCH() {
		Long valueVar = (Long) getTransactionVariableValue("SSNBRANCH");
		return valueVar;
	}

	/**
	 * Method to asign value for SSNBRANCH
	 */
	public void setVarSSNBRANCH(Object pSSNBRANCH) {
		setTransactionVariableValue("SSNBRANCH", convertObjToLong(pSSNBRANCH), Boolean.TRUE);
	}

	/**
	 * Method to get VLEXISTECE
	 * 
	 * @return Value for VLEXISTECE
	 */
	public String getVarVLEXISTECE() {
		return (String) getTransactionVariableValue("VLEXISTECE");
	}

	/**
	 * Method to asign value for VLEXISTECE
	 */
	public void setVarVLEXISTECE(Object pVLEXISTECE) {
		setTransactionVariableValue("VLEXISTECE", convertObjToString(pVLEXISTECE), Boolean.TRUE);
	}

	/**
	 * Method to get O_MSJ
	 * 
	 * @return Value for O_MSJ
	 */
	public String getVarO_MSJ() {
		return (String) getTransactionVariableValue("O_MSJ");
	}

	/**
	 * Method to asign value for O_MSJ
	 */
	public void setVarO_MSJ(Object pO_MSJ) {
		setTransactionVariableValue("O_MSJ", convertObjToString(pO_MSJ), Boolean.TRUE);
	}

	/**
	 * Method to get OSSNCENTRAL
	 * 
	 * @return Value for OSSNCENTRAL
	 */
	public Long getVarOSSNCENTRAL() {
		return (Long) getTransactionVariableValue("OSSNCENTRAL");
	}

	/**
	 * Method to asign value for OSSNCENTRAL
	 */
	public void setVarOSSNCENTRAL(Object pOSSNCENTRAL) {
		setTransactionVariableValue("OSSNCENTRAL", convertObjToLong(pOSSNCENTRAL), Boolean.TRUE);
	}

	/**
	 * Method to get VLTSN
	 * 
	 * @return Value for VLTSN
	 */
	public Long getVarVLTSN() {
		return (Long) getTransactionVariableValue("VLTSN");
	}

	/**
	 * Method to asign value for VLTSN
	 */
	public void setVarVLTSN(Object pVLTSN) {
		setTransactionVariableValue("VLTSN", convertObjToLong(pVLTSN), Boolean.TRUE);
	}

	/**
	 * Method for obtain O_FILIAL
	 *
	 * @return Value for O_FILIAL
	 */
	public String getO_FILIAL() {
		return (String) getTransactionVariableValue("O_FILIAL");
	}

	/**
	 * Method for asign value in O_FILIAL
	 */
	public void setO_FILIAL(Object pO_FILIAL) {
		setTransactionVariableValue("O_FILIAL", convertObjToString(pO_FILIAL), Boolean.TRUE);
	}

	/**
	 * Method for obtain O_RUC_FILIAL
	 *
	 * @return Value for O_RUC_FILIAL
	 */

	public String getO_RUC_FILIAL() {
		return (String) getTransactionVariableValue("O_RUC_FILIAL");
	}

	/**
	 * Method for asign value in O_RUC_FILIAL
	 */
	public void setO_RUC_FILIAL(Object pO_RUC_FILIAL) {
		setTransactionVariableValue("O_RUC_FILIAL", convertObjToString(pO_RUC_FILIAL), Boolean.TRUE);
	}

	/**
	 * Method for obtain O_DIREC_FILIAL
	 *
	 * @return Value for O_DIREC_FILIAL
	 */

	public String getO_DIREC_FILIAL() {
		return (String) getTransactionVariableValue("O_DIREC_FILIAL");
	}

	/**
	 * Method for asign value in O_DIREC_FILIAL
	 */
	public void setO_DIREC_FILIAL(Object pO_DIREC_FILIAL) {
		setTransactionVariableValue("O_DIREC_FILIAL", convertObjToString(pO_DIREC_FILIAL), Boolean.TRUE);
	}

	// ICTRLCHQ
	public Integer getVarCtrlCheck() {
		return (Integer) getTransactionVariableValue(Constants.RETURN_CONTROL_CHECK);
	}

	/**
	 * Method for asign value in O_DIREC_FILIAL
	 */
	public void setVarCtrlCheck(Object pVarCtrlCheck) {
		setTransactionVariableValue(Constants.RETURN_CONTROL_CHECK, convertObjToString(pVarCtrlCheck), Boolean.TRUE);
	}

	/* fin variables */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.action.executor.services.impl. IFinantialEngine#createFields()
	 */
	@Override
	public void createFields() {
		Composite attributeComposite = (Composite) this.getAttribute(FIELD_TXTNUMCUENTA);
		if (!getShowSearchAccounts()) {
			attributeComposite.setBtnEnabled(false);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.action.executor.services.impl. IFinantialEngine#getEventsAttributes()
	 */
	@Override
	public Map<String, Event> getEventsAttributes() {
		Map<String, Event> events = new HashMap<String, Event>();
		//
		List<Action> actionsTXTNUMCUENTA = new ArrayList<Action>();
		//addDigitVerifierAction(actionsTXTNUMCUENTA);
		Action actionSP_TR_QUERY_NOM_CTADED = new Action("sp_tr_query_nom_ctaded", transactionClassName, ActionType.SERVER, "", Boolean.TRUE);
		actionsTXTNUMCUENTA.add(actionSP_TR_QUERY_NOM_CTADED);
		Event eventTXTNUMCUENTA = new Event(Constants.EVENT_ATTRIBUTE, Constants.EVENT_ATTRIBUTE, actionsTXTNUMCUENTA);
		events.put(FIELD_TXTNUMCUENTA, eventTXTNUMCUENTA);
		//
		List<Action> actionsTXTEFECTIVO = new ArrayList<Action>();
		Action actionAddTXTTOTDEPOSITO = new Action("addTXTTOTDEPOSITO", transactionClassName, ActionType.SERVER, "", Boolean.FALSE);
		actionsTXTEFECTIVO.add(actionAddTXTTOTDEPOSITO);
		Event eventTXTEFECTIVO = new Event(Constants.EVENT_ATTRIBUTE, Constants.EVENT_ATTRIBUTE, actionsTXTEFECTIVO);
		events.put(FIELD_TXTEFECTIVO, eventTXTEFECTIVO);
		//
		List<Action> actionsTXTCHQLOCAL = new ArrayList<Action>();
		Action actionFUNCION_43 = new Action("FUNCION_43", transactionClassName, ActionType.SERVER, "", Boolean.TRUE);
		actionsTXTCHQLOCAL.add(actionFUNCION_43);
		actionsTXTCHQLOCAL.add(actionAddTXTTOTDEPOSITO);
		Event eventTXTCHQLOCAL = new Event(Constants.EVENT_ATTRIBUTE, Constants.EVENT_ATTRIBUTE, actionsTXTCHQLOCAL);
		events.put(FIELD_TXTCHQLOCAL, eventTXTCHQLOCAL);
		if (getShowChecks() && !getContext().getReverseMode()) {
			addActionDetailsCheck(events, FIELD_TXTCHQLOCAL, "popupVariablesDetailsCheck");
		}
        /*
		List<Action> actionsTXTCHQOTRASP = new ArrayList<Action>();
		Action actionFUNCION_44 = new Action("FUNCION_44", transactionClassName, ActionType.SERVER, "", Boolean.TRUE);
		actionsTXTCHQOTRASP.add(actionFUNCION_44);
		actionsTXTCHQOTRASP.add(actionAddTXTTOTDEPOSITO);
		Event eventTXTCHQOTRASP = new Event(Constants.EVENT_ATTRIBUTE, Constants.EVENT_ATTRIBUTE, actionsTXTCHQOTRASP);
		events.put(FIELD_TXTCHQOTRASP, eventTXTCHQOTRASP);
        */
		//
		List<Action> actionsTXTCHQPROPIO = new ArrayList<Action>();
		Action actionFUNCION_44 = new Action("FUNCION_44", transactionClassName, ActionType.SERVER, "", Boolean.TRUE);
		actionsTXTCHQPROPIO.add(actionFUNCION_44);
		actionsTXTCHQPROPIO.add(actionAddTXTTOTDEPOSITO);
		Event eventTXTCHQPROPIO = new Event(Constants.EVENT_ATTRIBUTE, Constants.EVENT_ATTRIBUTE, actionsTXTCHQPROPIO);
		events.put(FIELD_TXTCHQPROPIO, eventTXTCHQPROPIO);
		if (getShowChecks() && !getContext().getReverseMode()) {
			addActionDetailsCheck(events, FIELD_TXTCHQPROPIO, "popupVariablesDetailsCheckOwner");
		}

		return events;
	}

	/**
	 * Metodo que permite crear los botones por default de cualquier pantalla
	 */
	@Override
	public List<Event> getEventsDefault() {
		List<Event> events = new ArrayList<>();
		//
		List<Action> actionsFUNCIONF6 = new ArrayList<>();
		addVerifyPrintReady(actionsFUNCIONF6);
		actionsFUNCIONF6.add(new Action("FUNCION_3", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		actionsFUNCIONF6.add(new Action("sp_valida_montos", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		actionsFUNCIONF6.add(new Action("FUNCION_2", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		actionsFUNCIONF6.add(new Action(Constants.SUPERVISOR, transactionClassName, ActionType.CLIENT, Constants.SUPERVISOR_MODAL, Boolean.TRUE));
		// CLIENTE OCACIONAL
		if (getShowOcasionalClient()) {
			addOccasionalClientAction(actionsFUNCIONF6);
			addInsertOccasionalClientAction(actionsFUNCIONF6);
		}

		// Huella/Foto
		if (getShowFingerPrint() && getShowClientPhoto() && !getContext().getReverseMode()) {
			addBiometricValidation(actionsFUNCIONF6, "popupVariablesBiometricValidation");
		}

		// FORMULARIOS PENDIENTES
		if (getShowFormPend()) {
			// AQU: Se agrega la accion de licitud pendientes de fondos
			addActionFundsPendingDeclaration(actionsFUNCIONF6, "popupVariablesFundsPendingDeclaration");
		}
		actionsFUNCIONF6.add(new Action("FUNCION_13", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		actionsFUNCIONF6.add(new Action("sp_pago_aportaciones_local", transactionClassName, ActionType.SERVER, "", Boolean.TRUE));

		// FORMULARIO CONSEP\LICITUD 
		if (getShowFormCONSEP()) {
			// AQU: Se agrega la accion para crear formulario de licitud
			addActionFundsDeclaration(actionsFUNCIONF6, "popupVariablesFundsDeclaration");
		}

		// FORMULARIO SERIES
		if (getShowSeries()) {
			// AQU: Se agrega accion para ingresar series
			addActionInsertSeries(actionsFUNCIONF6, "popupVariablesInsertSeries");
		}
		// FORMULARIO SERIES
		if (getShowNotificationSmtp()) {
			// AQU: Se agrega accion para ingresar series
			addNotificationSMTP(actionsFUNCIONF6, "addParamSMTP");
		}

		/*if (Boolean.FALSE.equals(getContext().getReverseMode())) {
			addPrintAction(actionsFUNCIONF6, "printTransactionPAGAPORTACIONES1", "PAGAPORTACIONES1");
		}*/
		addPrintAction(actionsFUNCIONF6, "printTransactionPAGAPORTACIONES0", "PAGAPORTACIONES0");
		actionsFUNCIONF6.add(new Action("FUNCION_58", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		actionsFUNCIONF6.add(new Action("ACCIONBORRAR", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		actionsFUNCIONF6.add(new Action("FUNCION_47", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		events.add(new Event("F6", "FUNCIONF6", actionsFUNCIONF6, Boolean.TRUE));
		//
		List<Action> actionsFUNCIONF2 = new ArrayList<>();
		actionsFUNCIONF2.add(new Action("ACCIONBORRAR", transactionClassName, ActionType.SERVER, "", Boolean.FALSE));
		events.add(new Event("F2", "FUNCIONF2", actionsFUNCIONF2, Boolean.FALSE));

		// RE-IMPRESION
		List<Action> actionsReimpresion = new ArrayList<>();
		addActionPrintLast(actionsReimpresion);
		events.add(new Event(Constants.TECLAS_RE_IMPRESION, "REIMPRESION", actionsReimpresion, Boolean.FALSE));

		// ACCION BOTON COMPOSITE
		if (getShowSearchAccounts()) {
			List<Action> actionsButtonComposite = new ArrayList<>();
			addActionAccount(actionsButtonComposite);
			addDigitVerifierAction(actionsButtonComposite);
			Action actionSP_TR_QUERY_NOM_CTADED = new Action("sp_pago_aportaciones_local", transactionClassName, ActionType.SERVER, "", Boolean.FALSE);
			actionsButtonComposite.add(actionSP_TR_QUERY_NOM_CTADED);
			addEventCompositeAttribute(events, actionsButtonComposite, FIELD_TXTNUMCUENTA);
		}
		
	
		return events;
	}

	// Create abstract methods for logic action
	public abstract void VERIFICARCHEQUEOS();

	public abstract void FUNCION_25();

	public abstract void FUNCION_26();

	public abstract void FUNCION_27();

	public abstract void FUNCION_28();

	public abstract void FUNCION_29();

	public abstract void ACCIONSALIR();

	public abstract void FUNCION_2();

	public abstract void FUNCION_3();

	public abstract void FUNCION_4();

	public abstract void FUNCION_5();

	public abstract void FUNCION_6();

	public abstract void FUNCION_7();

	public abstract void FUNCION_8();

	public abstract void FUNCION_9();

	public abstract void FUNCION_10();

	public abstract void FUNCION_49();

	public abstract void FUNCION_22();

	public abstract void FUNCION_23();

	public abstract void FUNCION_24();

	public abstract void FUNCION_60();

	public abstract void FUNCION_61();

	public abstract void FUNCION_11();

	public abstract void FUNCION_12();

	public abstract void FUNCION_56();

	public abstract void FUNCION_14();

	public abstract void FUNCION_57();

	public abstract void FUNCION_53();

	public abstract void ACCIONIMPRIMIRFACTURA();

	public abstract void FUNCION_54();

	public abstract void FUNCION_58();

	public abstract void FUNCION_59();

	public abstract void FUNCION_39();

	public abstract void FUNCION_13();

	public abstract void FUNCION_47();

	public abstract void FUNCION_40();

	public abstract void FUNCION_48();

	public abstract void FUNCION_15();

	public abstract void FUNCION_16();

	public abstract void FUNCION_17();

	public abstract void FUNCION_45();

	// Create abstract methods for logic action for attributes
	public abstract void FUNCION_18();

	public abstract void FUNCION_19();

	public abstract void FUNCION_20();

	public abstract void FUNCION_21();

	public abstract void FUNCION_34();

	public abstract void FUNCION_50();

	public abstract void FUNCION_35();

	public abstract void FUNCION_41();

	public abstract void FUNCION_42();

	public abstract void FUNCION_43();

	public abstract void FUNCION_44();
	
	public abstract void FUNCION_51();

	public String getVarVGMODO() {
		return (getContext().getReverseMode()) ? "REV" : " ";
	}

	// Create methods for print action
	public void printTransactionPAGAPORTACIONES1() throws COBISException {
		String template = "PAGAPORTACIONES1";
		List<String> values = new ArrayList<>();

		/* Datos de la cabecera */
		values.add(convertObjToString(getO_FILIAL()));// 1
		values.add(convertObjToString(getO_RUC_FILIAL()));// 2
		values.add(convertObjToString(getO_DIREC_FILIAL()));// 3
		values.add(convertObjToString(getVarVLTSN())); // 4
		values.add(convertObjToString(getVarVGHORA())); // 5
		values.add(convertObjToString(ServerParamUtil.getProcessDate())); // 6
		values.add(convertObjToString(getVarVGOPERADOR())); // 7
		values.add(convertObjToString(getVarSSNBRANCH())); // 8
		values.add("ON"); // 9
		values.add(getContext().getReverseMode() == Boolean.TRUE ? "REV" : " ");// 10
		values.add("2784");// 11
		values.add(convertObjToString(getVarVGTRANSACCION() + "-" + getTransactionId())); // 12
		values.add(convertObjToString(getVarVGOFICINA() + "-" + getOfficeName()));// 13
		values.add(convertObjToString(getVarVGMONEDA() + "-" + getVarVGDESCMONEDA()));// 14

		/* Datos de la transacción */
		values.add(getTXTNUMCUENTA());// 15
		values.add(getTXTNOMBRE());// 16
		values.add(convertFormatToCurrency(getTXTTOTDEPOSITO() == null ? "0" : getTXTTOTDEPOSITO().toString()));// 17
	    values.add(convertFormatToCurrency(getVarSALDO_DISPONIBLE() == null ? "0" : getVarSALDO_DISPONIBLE().toString()));// 18

		try {
			saveLastImpression(getVarOSSNCENTRAL(), template, values, "N");
		} catch (Exception e) {
			LOGGER.logError("ERROR printTransactionPAGAPORTACIONES1" + e);
		} finally {
			printTransaction(values, template);
		}
	}

	public void printTransactionPAGAPORTACIONES0() throws COBISException {
		String template = "PAGAPORTACIONES0";
		List<String> values = new ArrayList<String>();

		/* Datos de la cabecera */
		values.add(convertObjToString(getO_FILIAL()));// 1
		values.add(convertObjToString(getO_RUC_FILIAL()));// 2
		values.add(convertObjToString(getO_DIREC_FILIAL()));// 3
		values.add(convertObjToString(getVarVLTSN())); // 4
		values.add(convertObjToString(getVarVGHORA())); // 5
		values.add(convertObjToString(ServerParamUtil.getProcessDate())); // 6
		values.add(convertObjToString(getVarVGOPERADOR())); // 7
		values.add(convertObjToString(getVarSSNBRANCH())); // 8
															// values.add(convertObjToString(getVarOSSNCENTRAL()));
		values.add("ON"); // 9
		values.add(convertObjToString(getContext().getReverseMode() == Boolean.TRUE ? "REV" : " "));// 10
		values.add("2784");// 11
		values.add(convertObjToString(getVarVGTRANSACCION())); // 12
		values.add(convertObjToString(getVarVGOFICINA() + "-" + getOfficeName()));// 13
		values.add(convertObjToString(getVarVGMONEDA() + "-" + getVarVGDESCMONEDA()));// 14

		/* Datos de la transacción */
		values.add(getTXTNUMCUENTA());// 15
		values.add(getTXTNOMBRE());// 16
		
		values.add(convertFormatToCurrency(getTXTTOTDEPOSITO() == null ? "0" : getTXTTOTDEPOSITO().toString()));// 17
		/*values.add(convertObjToString(getTransactionVariableValue("userSupervisor")));// 21
		values.add(convertObjToString(getTransactionVariableValue("userReasonSupervisor")));// 22
		values.add(convertObjToString(getTransactionVariableValue("userObservationSupervisor")));// 23
        */
	    values.add(convertFormatToCurrency(getVarSALDO_DISPONIBLE() == null ? "0" : getVarSALDO_DISPONIBLE().toString()));// 18
		
		try {
			saveLastImpression(getVarSSNBRANCH(), template, values, "S");
		} catch (Exception e) {
			LOGGER.logError("ERROR printTransactionPAGAPORTACIONES0" + e);
		} finally {
			printTransaction(values, template);
		}
	}

	/**
	 * Clean values in screen
	 */
	public void ACCIONBORRAR() {
		getContext().reloadTransaction();
	}

	public void sp_valida_montos() {
		try {
			List<ParameterSP> parameters = new ArrayList<ParameterSP>();
			parameters.add(new ParameterSP(ParameterType.IN, "@t_trn", SqlType.INT, "13016"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_trn", SqlType.INT, "26319"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_mon", SqlType.TINYINT, (getVarVGMONEDA() == null ? "" : getVarVGMONEDA())));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_cta", SqlType.VARCHAR, (getTXTNUMCUENTA() == null ? "" : getTXTNUMCUENTA())));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_efe", SqlType.MONEY, (getTXTEFECTIVO() == null ? "" : convertObjToString(getTXTEFECTIVO()))));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_chq_propios", SqlType.MONEY, (getTXTCHQPROPIO() == null ? "" : convertObjToString(getTXTCHQPROPIO()))));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_chq_locales", SqlType.MONEY, (getTXTCHQLOCAL() == null ? "" : convertObjToString(getTXTCHQLOCAL()))));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_chq_ot_plazas", SqlType.MONEY, (getTXTCHQOTRASP() == null ? "" : convertObjToString(getTXTCHQOTRASP()))));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_valor", SqlType.MONEY, (getTXTTOTDEPOSITO() == null ? "" : convertObjToString(getTXTTOTDEPOSITO()))));
			parameters.add(new ParameterSP(ParameterType.OUT, "@o_super", SqlType.CHAR, (getVarAUTORIZA() == null ? "00" : getVarAUTORIZA())));
			List<String> outputParameters = new ArrayList<String>();
			outputParameters.add("@o_super");
			Map<String, Object> respuesta = executeSP(parameters, Parameter.getLocalServerName().concat(".cobis.sp_valida_montos"), outputParameters, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
			if (getContext().getBaseActionEntity().isSuccess()) {
				setVarAUTORIZA(respuesta.get("@o_super"));
			} else {
				setVarAUTORIZA(null);
				failAction("sp_valida_montos");
			}
		} catch (Exception ex) {
			LOGGER.logError("Execute action sp_valida_montos with error", ex);
			failAction("sp_valida_montos");
		}
	}
	
	public void sp_pago_aportaciones_local() {
		try {
			List<ParameterSP> parameters = new ArrayList<ParameterSP>();
			if (!checkRepeatTransfer()) {
				parameters.add(new ParameterSP(ParameterType.IN, "@t_trn", SqlType.INT, "26060"));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_trn", SqlType.INT, "2784"));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_idcierre", SqlType.INT, "0"));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_sld_caja", SqlType.MONEY, "0"));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_id_ce", SqlType.VARCHAR, (getVarVLCEDULA() == null ? "" : getVarVLCEDULA())));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_existe_ce", SqlType.VARCHAR, (getVarVLEXISTECE() == null ? "" : convertObjToString(getVarVLEXISTECE()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_ente_ce", SqlType.INT, (getVarVLCODOCACIONAL() == null ? "" : convertObjToString(getVarVLCODOCACIONAL()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_nombre_ocx", SqlType.VARCHAR, "PAGAPORTACIONES"));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_cta", SqlType.VARCHAR, (getTXTNUMCUENTA() == null ? "" : getTXTNUMCUENTA())));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_efe", SqlType.MONEY, (getTXTEFECTIVO() == null ? "" : convertObjToString(getTXTEFECTIVO()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_prop", SqlType.MONEY, (getTXTCHQPROPIO() == null ? "" : convertObjToString(getTXTCHQPROPIO()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_loc", SqlType.MONEY, (getTXTCHQLOCAL() == null ? "" : convertObjToString(getTXTCHQLOCAL()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_plaz", SqlType.MONEY, (getTXTCHQOTRASP() == null ? "" : convertObjToString(getTXTCHQOTRASP()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_total", SqlType.MONEY, (getTXTTOTDEPOSITO() == null ? "" : convertObjToString(getTXTTOTDEPOSITO()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_ctrlchq", SqlType.INT,
						(getValueDetailCheck(FIELD_TXTCHQLOCAL) == null ? "" : convertObjToString(getValueDetailCheck(FIELD_TXTCHQLOCAL).toString()))));

				parameters.add(new ParameterSP(ParameterType.IN, "@i_ind", SqlType.TINYINT, ""));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_cau", SqlType.VARCHAR, ""));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_mon", SqlType.TINYINT, (getVarVGMONEDA() == null ? "" : convertObjToString(getVarVGMONEDA()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_dep", SqlType.SMALLINT, ""));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_concep", SqlType.VARCHAR, (getCONCEPTO() == null ? "" : convertObjToString(getCONCEPTO()))));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_nombre_cta", SqlType.VARCHAR, (getTXTNOMBRE() == null ? "" : convertObjToString(getTXTNOMBRE()))));

				parameters.add(new ParameterSP(ParameterType.OUT, "@o_sldcaja", SqlType.MONEY, (getVarSALDO_CAJA() == null ? "" : convertObjToString(getVarSALDO_CAJA()))));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_idcierre", SqlType.TINYINT, convertObjToString(getVarID_CIERRE())));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_ssn_host", SqlType.INT, (getVarOSSNCENTRAL() == null ? "" : convertObjToString(getVarOSSNCENTRAL()))));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_formulario", SqlType.CHAR, (getVarVLGENERAFORM() == null ? "" : convertObjToString(getVarVLGENERAFORM()))));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_monto_formulario", SqlType.MONEY, (getVarMONTO_FORMULARIO() == null ? "" : convertObjToString(getVarMONTO_FORMULARIO()))));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_ruc_filial", SqlType.VARCHAR, (getO_RUC_FILIAL() == null ? "00" : getO_RUC_FILIAL())));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_direc_filial", SqlType.VARCHAR, (getO_DIREC_FILIAL() == null ? "00" : getO_DIREC_FILIAL())));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_nombre_filial", SqlType.VARCHAR, (getO_FILIAL() == null ? "00" : getO_FILIAL())));
				parameters.add(new ParameterSP(ParameterType.OUT, "@o_saldo_total", SqlType.MONEY, (getVarSALDO_DISPONIBLE() == null ? "" : convertObjToString(getVarSALDO_DISPONIBLE()))));

				if (getContext().getReverseMode()) {
					parameters.add(new ParameterSP(ParameterType.IN, "@t_corr", SqlType.VARCHAR, "S"));
					parameters.add(new ParameterSP(ParameterType.IN, "@t_ssn_corr", SqlType.INT, convertObjToString(getTransactionVariableValue("ssn_branch"))));
					addParameterOtherUserReverse(parameters);
					parameters.add(new ParameterSP(ParameterType.IN, "@t_tsn_aut", SqlType.INT, convertObjToString(getTransactionVariableValue("tsn_aut"))));
					addParameterUserSupervisor(parameters);
					parameters.add(new ParameterSP(ParameterType.IN, "@i_login_aut_reverso", SqlType.VARCHAR,
							(getTransactionVariableValue("userSupervisor") == null ? "" : convertObjToString(getTransactionVariableValue("userSupervisor")))));
					parameters.add(new ParameterSP(ParameterType.IN, "@i_motivo_reverso", SqlType.VARCHAR,
							(getTransactionVariableValue("userReasonSupervisor") == null ? "" : convertObjToString(getTransactionVariableValue("userReasonSupervisor")).split(" -")[0])));
					parameters.add(new ParameterSP(ParameterType.IN, "@i_obs_reverso", SqlType.VARCHAR,
							(getTransactionVariableValue("userObservationSupervisor") == null ? "" : convertObjToString(getTransactionVariableValue("userObservationSupervisor")))));

				}
				List<String> outputParameters = new ArrayList<String>();
				outputParameters.add("@o_sldcaja");
				outputParameters.add("@o_idcierre");
				outputParameters.add("@o_ssn_host");
				outputParameters.add("@o_formulario");
				outputParameters.add("@o_monto_formulario");
				outputParameters.add("@o_ruc_filial");
				outputParameters.add("@o_direc_filial");
				outputParameters.add("@o_nombre_filial");
				outputParameters.add("@o_saldo_total");
				Map<String, Object> respuesta = executeSP(parameters, Parameter.getLocalServerName().concat(".cob_remesas.sp_pago_aportaciones_local"), outputParameters, Boolean.TRUE, Boolean.TRUE,
						Boolean.TRUE);
				if (getContext().getBaseActionEntity().isSuccess()) {
					setVarSALDO_CAJA(respuesta.get("@o_sldcaja"));
					setVarID_CIERRE(respuesta.get("@o_idcierre"));
					setVarSSNBRANCH(respuesta.get("@o_ssn_host"));
					setVarVLGENERAFORM(respuesta.get("@o_formulario"));
					setVarMONTO_FORMULARIO(respuesta.get("@o_monto_formulario"));
					setO_RUC_FILIAL(respuesta.get("@o_ruc_filial"));
					setO_DIREC_FILIAL(respuesta.get("@o_direc_filial"));
					setO_FILIAL(respuesta.get("@o_nombre_filial"));
					setVarSALDO_DISPONIBLE(respuesta.get("@o_saldo_total"));

					if (getVarO_MSJ() != null) {
						if (getVarO_MSJ().compareTo("0") != 0 && !getContext().getReverseMode()) {
							this.addInfoMessage(getVarO_MSJ().toString(), 111);
						}
					}
					setVarVLTSN(getTsn(convertObjToString(getVarVGOPERADOR())));
					addSuccessMessage("Transacción exitosa", 0);
					addVariablesForValidateTransfer("F6");
				} else {
					setVarSALDO_CAJA(null);
					setVarID_CIERRE(null);
					setVarSSNBRANCH(null);
					setVarSALDO_DISPONIBLE(null);
					setVarFORMULARIO(respuesta.get(null));
					setVarMONTO_FORMULARIO(respuesta.get(null));
					setO_RUC_FILIAL(respuesta.get(null));
					setO_DIREC_FILIAL(respuesta.get(null));
					setO_FILIAL(respuesta.get(null));
					failAction("sp_pago_aportaciones_local");
					addErrorMessage("Error en la transacción", 111);
				}
			} else {
				failAction("sp_ah_depositosl_local");
				addErrorMessage(Constants.MENSAJE_TRANSACTION_REPEAT, 999);
			}

		} catch (Exception ex) {
			LOGGER.logError("Execute action sp_pago_aportaciones_local with error", ex);
			failAction("sp_pago_aportaciones_local");
		}
		LOGGER.logDebug("FINALIZA sp_pago_aportaciones_local.......");
	}
		    
	public void sp_tr_query_nom_ctaded() {
		try {
							
				List<ParameterSP> parameters = new ArrayList<ParameterSP>();
				parameters.add(new ParameterSP(ParameterType.IN, "@t_trn", SqlType.INT, "2781"));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_operacion", SqlType.VARCHAR, "C"));
			    parameters.add(new ParameterSP(ParameterType.IN, "@i_cod_producto", SqlType.CHAR, "2"));	
				parameters.add(new ParameterSP(ParameterType.IN, "@i_cta", SqlType.VARCHAR, (getTXTNUMCUENTA() == null ? "" : getTXTNUMCUENTA())));
				parameters.add(new ParameterSP(ParameterType.IN, "@i_mon", SqlType.TINYINT, (getVarVGMONEDA() == null ? "" : convertObjToString(getVarVGMONEDA()))));

				List<String> outputParameters = new ArrayList<String>();
				Map<String, Object> respuesta = executeFormAndSP(parameters, "cob_remesas.sp_pagos_internos", outputParameters, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
				if (getContext().getBaseActionEntity().isSuccess()) {
					if (respuesta != null && !respuesta.isEmpty()) {
						List<Object[]> resp = (List<Object[]>) respuesta.get("rs1");
						for (Object[] obj : resp) {
							if (obj != null && !"".equals(obj[0])) {
								setTXTNOMBRE(obj[0].toString());
							}
						}
					}
				} else {
					setTXTNUMCUENTA(null);
					setTXTNOMBRE(null);
					addInfoMessage("CUENTA NO CORRESPONDE A UNA APORTACIÓN O ESTÁ INACTIVA", 111);					
					failAction("sp_pagos_internos");
				}
			
		} catch (Exception ex) {
			LOGGER.logError("Execute action sp_pagos_internos with error", ex);
			failAction("sp_pagos_internos");
		}
		LOGGER.logDebug("FINALIZA sp_pagos_internos.......");
	}

	public void sp_datos_cli_branch() {
		try {
			List<ParameterSP> parameters = new ArrayList<ParameterSP>();
			parameters.add(new ParameterSP(ParameterType.IN, "@t_trn", SqlType.INT, "26492"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_operacion", SqlType.VARCHAR, "Q"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_ente", SqlType.INT, (getTXTCODOCACIONAL() == null ? "" : getTXTCODOCACIONAL())));
			parameters.add(new ParameterSP(ParameterType.OUT, "@o_ced_ruc", SqlType.VARCHAR, (getVarVLCEDULA() == null ? "00" : getVarVLCEDULA())));
			parameters.add(new ParameterSP(ParameterType.OUT, "@o_direccion", SqlType.VARCHAR, (getVarVLDIRECCION() == null ? "00" : getVarVLDIRECCION())));
			parameters.add(new ParameterSP(ParameterType.OUT, "@o_telefono", SqlType.VARCHAR, (getVarVLTELEFONO() == null ? "00" : getVarVLTELEFONO())));
			parameters.add(new ParameterSP(ParameterType.OUT, "@o_tipo_doc_desc", SqlType.VARCHAR, (getVarVLTIPOID() == null ? "00" : getVarVLTIPOID())));
			parameters.add(new ParameterSP(ParameterType.OUT, "@o_nombre", SqlType.VARCHAR, (getVarVLNOMBRE() == null ? "00" : getVarVLNOMBRE())));
			List<String> outputParameters = new ArrayList<String>();
			outputParameters.add("@o_ced_ruc");
			outputParameters.add("@o_direccion");
			outputParameters.add("@o_telefono");
			outputParameters.add("@o_tipo_doc_desc");
			outputParameters.add("@o_nombre");
			Map<String, Object> respuesta = executeSP(parameters, "cobis.sp_datos_cli_branch", outputParameters, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
			if (getContext().getBaseActionEntity().isSuccess()) {
				setVarVLCEDULA(respuesta.get("@o_ced_ruc"));
				setVarVLDIRECCION(respuesta.get("@o_direccion"));
				setVarVLTELEFONO(respuesta.get("@o_telefono"));
				setVarVLTIPOID(respuesta.get("@o_tipo_doc_desc"));
				setVarVLNOMBRE(respuesta.get("@o_nombre"));
			} else {
				setVarVLCEDULA(null);
				setVarVLDIRECCION(null);
				setVarVLTELEFONO(null);
				setVarVLTIPOID(null);
				setVarVLNOMBRE(null);
				failAction("sp_datos_cli_branch");
			}
		} catch (Exception ex) {
			LOGGER.logError("Execute action sp_datos_cli_branch with error", ex);
			failAction("sp_datos_cli_branch");
		}
	}

	@SuppressWarnings("unused")
	public void sp_valida_lavado_dinero() {
		try {
			List<ParameterSP> parameters = new ArrayList<ParameterSP>();
			parameters.add(new ParameterSP(ParameterType.IN, "@t_trn", SqlType.INT, "26410"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_userorg", SqlType.VARCHAR, (getVarVGLOGIN() == null ? "" : convertObjToString(getVarVGLOGIN()))));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_com", SqlType.VARCHAR, (getVarVLCOM() == null ? "" : getVarVLCOM())));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_mon", SqlType.TINYINT, (getVarVGMONEDA() == null ? "" : convertObjToString(getVarVGMONEDA()))));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_trn", SqlType.INT, "26319"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_val", SqlType.MONEY, (getTXTEFECTIVO() == null ? "" : convertObjToString(getTXTEFECTIVO()))));
			List<String> outputParameters = new ArrayList<String>();
			Map<String, Object> respuesta = executeSP(parameters, Parameter.getLocalServerName().concat(".cobis.sp_valida_lavado_dinero"), outputParameters, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);
			if (getContext().getBaseActionEntity().isSuccess()) {
			} else {
				failAction("sp_valida_lavado_dinero");
			}
		} catch (Exception ex) {
			LOGGER.logError("Execute action sp_valida_lavado_dinero with error", ex);
			failAction("sp_valida_lavado_dinero");
		}
	}

	@SuppressWarnings("unchecked")
	public void sp_tr_query_nom_ctahorro() {
		try {
			List<ParameterSP> parameters = new ArrayList<ParameterSP>();
			parameters.add(new ParameterSP(ParameterType.IN, "@t_trn", SqlType.INT, "206"));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_cta", SqlType.VARCHAR, (getTXTNUMCUENTA() == null ? "" : getTXTNUMCUENTA())));
			parameters.add(new ParameterSP(ParameterType.IN, "@i_mon", SqlType.TINYINT, (getVarVGMONEDA() == null ? "" : convertObjToString(getVarVGMONEDA()))));
			List<String> outputParameters = new ArrayList<String>();
			Map<String, Object> respuesta = executeFormAndSP(parameters, "cob_ahorros.sp_tr_query_nom_ctahorro", outputParameters, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
			if (getContext().getBaseActionEntity().isSuccess()) {
				for (int indexResp = 1; indexResp <= respuesta.size(); indexResp++) {
					String nameResponse = "rs" + indexResp;
					List<Object[]> listResp = (List<Object[]>) respuesta.get(nameResponse);

					if ("rs1".equals(nameResponse)) {
						setTXTNOMBRE(listResp.get(1)[0] == null ? "" : (String) listResp.get(1)[0]);
					}
				}
			} else {
				setTXTNOMBRE(null);
				setTXTNUMCUENTA(null);
				failAction("sp_tr_query_nom_ctahorro");
				getContext().reloadTransaction();
			}
		} catch (Exception ex) {
			LOGGER.logError("Execute action sp_tr_query_nom_ctahorro with error", ex);
			failAction("sp_tr_query_nom_ctahorro");
		}
	}

	/**
	 * Method to verified account with digit verifier to TXTNUMCUENTA
	 */
	public void digitVerifier() {
		super.digitVerifier(FIELD_TXTNUMCUENTA, "AHO", getContextCurrency().getKey());
	}

	public void addTXTTOTDEPOSITO() {
		BigDecimal tmp_TXTEFECTIVO = (getTXTEFECTIVO() == null) ? BigDecimal.ZERO : getTXTEFECTIVO();
		BigDecimal tmp_TXTCHQPROPIO = (getTXTCHQPROPIO() == null) ? BigDecimal.ZERO : getTXTCHQPROPIO();
		BigDecimal tmp_TXTCHQLOCAL = (getTXTCHQLOCAL() == null) ? BigDecimal.ZERO : getTXTCHQLOCAL();
		BigDecimal tmp_TXTCHQOTRASP = (getTXTCHQOTRASP() == null) ? BigDecimal.ZERO : getTXTCHQOTRASP();
		BigDecimal totalAdd = BigDecimal.ZERO;
		totalAdd = totalAdd.add(tmp_TXTEFECTIVO);
		totalAdd = totalAdd.add(tmp_TXTCHQPROPIO);
		totalAdd = totalAdd.add(tmp_TXTCHQLOCAL);
		totalAdd = totalAdd.add(tmp_TXTCHQOTRASP);
		setFieldValue(FIELD_TXTTOTDEPOSITO, totalAdd);
	}

	// DECLARACION DE FUNCION PARA MECANISMO DE FORMULARIOS PENDIENTES
	public void popupVariablesFundsPendingDeclaration() {
		try {
			popupVariablesFundsPendingDeclaration(getFieldValue(FIELD_TXTNOMBRE), getFieldValue(FIELD_TXTNUMCUENTA), Constants.PRODUCT_CTA_AHO);
		} catch (Exception e) {

			LOGGER.logDebug("error popupVariablesFundsPendingDeclaration", e);
			failAction("popupVariablesFundsPendingDeclaration");
		}
	}

	// DECLARACION DE FUNCION PARA MECANISMO DE FORMULARIOS CONSEP\LICITUD
	public void popupVariablesFundsDeclaration() {
		try {
			LOGGER.logDebug("valor que retorna el SP: " + getVarVLGENERAFORM());
			if (getVarVLGENERAFORM() != null && !"N".equals(convertObjToString(getVarVLGENERAFORM())) && !"".equals(String.valueOf(getVarVLGENERAFORM()).trim())) {
				popupVariablesFundsDeclaration(getFieldValue(FIELD_TXTNOMBRE), getFieldValue(FIELD_TXTNUMCUENTA), getFieldValue(FIELD_TXTTOTDEPOSITO), Constants.PRODUCT_CTA_AHO,
						convertObjToString("252"), convertObjToBigDecimal(getVarMONTO_FORMULARIO()));
			} else {
				cancelActionFundsDeclaration("popupVariablesFundsDeclaration");
			}

		} catch (Exception e) {

			LOGGER.logDebug("error popupVariablesFundsDeclaration", e);
			failAction("popupVariablesFundsDeclaration");
		}
	}

	// DECLARACION DE FUNCION PARA MECANISMO DE SERIES
	/** Se agrega accion para levantar la modal de series */
	public void popupVariablesInsertSeries() {
		try {
			popupVariablesInsertSeries(getFieldValue(FIELD_TXTNOMBRE), getFieldValue(FIELD_TXTNUMCUENTA), getFieldValue(FIELD_TXTEFECTIVO), Constants.PRODUCT_CTA_AHO,
					convertObjToString(getVarSSNBRANCH()));
		} catch (Exception e) {

			LOGGER.logDebug("error popupVariablesInsertSeries", e);
			failAction("popupVariablesInsertSeries");
		}
	}

	// DECLARACION DE FUNCION PARA MECANISMO CHEQUES
	/* metodo que se ejecuta en cheques locales */
	public void popupVariablesDetailsCheck() {
		if(getTXTNUMCUENTA() == null || "".equals(getTXTNUMCUENTA())) {
			failAction("popupVariablesDetailsCheck");
			addInfoMessage("No existe cuenta",0);
		}else {
			try {
				popupVariablesDetailsCheck("L", "AHO", Constants.PRODUCT_CTA_AHO, FIELD_TXTCHQLOCAL);
				HashMap values = (HashMap) getTransactionVariableValue("popupParams");
				this.setTransactionVariableValue("popupParams", values);
			} catch (Exception e) {

				LOGGER.logDebug("error popupVariablesDetailsCheck", e);
				failAction("popupVariablesDetailsCheck");
			}
		}
		
	}
	

	public void popupVariablesDetailsCheckOtraP() {
		try {
			popupVariablesDetailsCheck("E", "AHO", Constants.PRODUCT_CTA_AHO, FIELD_TXTCHQOTRASP);
		} catch (Exception e) {

			LOGGER.logDebug("error popupVariablesDetailsCheckOtraP", e);
			failAction("popupVariablesDetailsCheckOtraP");
		}
	}

	public void popupVariablesDetailsCheckOwner() {
		if(getTXTNUMCUENTA() == null || "".equals(getTXTNUMCUENTA())) {
			addInfoMessage("No existe cuenta", 0);
			failAction("popupVariablesDetailsCheckOwner");
		}else {
			try {
				popupVariablesDetailsCheck("P", "AHO", Constants.PRODUCT_CTA_AHO, FIELD_TXTCHQPROPIO);
				HashMap values = (HashMap) getTransactionVariableValue("popupParams");
				this.setTransactionVariableValue("popupParams", values);
			} catch (Exception e) {

				LOGGER.logDebug("error popupVariablesDetailsCheckOwner", e);
				failAction("popupVariablesDetailsCheckOwner");
			}
		}
		
	}

	public void popupVariablesBiometricValidation() {
		try {
			popupVariablesBiometricValidation(getFieldValue(FIELD_TXTNUMCUENTA), getFieldValue(FIELD_TXTTOTDEPOSITO), "2784", Constants.PRODUCT_CTA_AHO);
		} catch (Exception e) {

			LOGGER.logDebug("error popupVariablesBiometricValidation", e);
			failAction("popupVariablesBiometricValidation");
		}
	}

	public void popupVariablesAccount() {
		try {
			setTXTNOMBRE(null);
			popupVariablesAccount("4", FIELD_TXTNUMCUENTA);
		} catch (Exception e) {

			LOGGER.logDebug("error popupVariablesAccounts", e);
			failAction("popupVariablesAccounts");
		}
	}

	// DECLARACION DE FUNCION DE SMTP
	/** Se agrega accion ejecutar el smtp */
	public void addParamSMTP() {
		try {
			Object today = Calendar.getInstance().getTime();
			String hour = ConvertUtil.dateToString(today, "HH:mm:ss");
			String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<data>" + "<cuenta>" + getTXTNUMCUENTA() + "</cuenta>" + "<name>" + getTXTNOMBRE() + "</name>" + "<name2>"
					+ convertObjToString(getVarVGOPERADOR()) + "</name2>" + "<tran>Deposito Ahorros Sin Libreta</tran>" + "<mount>"
					+ convertFormatToCurrency(getTXTTOTDEPOSITO() == null ? "0" : getTXTTOTDEPOSITO().toString()) + "</mount>" + "<date>" + hour + "</date>" + "</data>";
			addParametersNotificationSMTP(getTXTNUMCUENTA(), getTXTNOMBRE(), getTXTTOTDEPOSITO().toString(), "26319", "4", null, null, body, 'S', -1);
		} catch (Exception e) {
			LOGGER.logDebug("error addParamSMTP", e);
			failAction("addParamSMTP");
		}
	}

}
