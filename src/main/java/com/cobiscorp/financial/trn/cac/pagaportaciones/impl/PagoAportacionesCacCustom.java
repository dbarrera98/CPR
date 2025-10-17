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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.cobiscorp.cobis.engine.services.Constants;
import com.cobiscorp.cobis.finantial.engine.action.executor.services.impl.IFinantialEngine;
import com.cobiscorp.cobis.finantial.engine.dto.Attribute;
import com.cobiscorp.cobis.finantial.engine.dto.Composite;

@Component
@Service(value = IFinantialEngine.class)
public class PagoAportacionesCacCustom extends PagoAportacionesCacGen {

	/**
	 * Default constructor
	 */
	public PagoAportacionesCacCustom() {
		super(PagoAportacionesCacCustom.class.getName());
	}

	public PagoAportacionesCacCustom(String name) {
		super(name);
	}

	@Override
	public void createFields() {
		// TODO Auto-generated method stub
		super.createFields();
		Attribute fieldTxtEfectivo = this.getAttribute(FIELD_TXTEFECTIVO);
		fieldTxtEfectivo.setSumadora(Boolean.TRUE);
		if (!getContext().getReverseMode()) {
			if (getShowSumadora()) {
				fieldTxtEfectivo.setEnabled(false);
			} else {
				fieldTxtEfectivo.setEnabled(true);
			}
		} else {
			fieldTxtEfectivo.setEnabled(false);
		}

		/* llamar a cheque si tiene los permisos S:true, N:false */
		Attribute fieldChequeLocal = this.getAttribute(FIELD_TXTCHQLOCAL);
		Attribute fieldChequeOtras = this.getAttribute(FIELD_TXTCHQOTRASP);
		if (getContext().getReverseMode()) {
			fieldChequeLocal.setEnabled(false);
			fieldChequeOtras.setEnabled(false);
		}
		// Oculta el botón de búsqueda para el reverso
		Composite attributeComposite = (Composite) this.getAttribute(FIELD_TXTNUMCUENTA);
		if (getContext().getReverseMode()) {
			attributeComposite.setBtnEnabled(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#VERIFICARCHEQUEOS()
	 */
	@Override
	public void VERIFICARCHEQUEOS() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_25()
	 */
	@Override
	public void FUNCION_25() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then parValorRetorno=True Else parValorRetorno=False End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_26()
	 */
	@Override
	public void FUNCION_26() {
		/*
		 * 
		 * Dim parMonto As Double Dim parMoneda As Integer Dim parTransaccion As String Dim parCtaBco As String Dim parCodTitular As Integer Dim
		 * parRetornocc As Boolean Dim parInfTran As String
		 * 
		 * Dim desmontrn As String Dim total As String Dim total1 As String
		 * 
		 * desmontrn = TransactionExecutor1.FMGetVariableGlobal("VGDescMoneda") total = TransactionExecutor1.FMGetObjeto("LBLTOTCONV").Text
		 * TransactionExecutor1.FMGetObjeto("TXTLETRAS").Text= TransactionExecutor1.FMNumLetras(total,desmontrn)
		 * total1=TransactionExecutor1.FMGetObjeto("TXTTOTDEPOSITO").Text TransactionExecutor1.FMGetObjeto("TXTLETRAS1").Text=
		 * TransactionExecutor1.FMNumLetras(total1,desmontrn)
		 * 
		 * parMonto=Convert.ToDecimal(TransactionExecutor1.FMGetObjeto("txtTotDeposito").text, System.Globalization.CultureInfo.InvariantCulture)
		 * parMoneda = TransactionExecutor1.FMGetVariableGlobal("VGMoneda") parTransaccion = "252" parCodTitular =
		 * TransactionExecutor1.FMGetObjeto("VLCODTITULAR").valor parCtaBco = TransactionExecutor1.FMGetObjeto("txtNumCuenta").Cliptext parInfTran =
		 * "S" VLMco = VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX")
		 * 
		 * TransactionExecutor1.FMGetObjeto("VL_NEM_BOL").valor =
		 * VLMco.ATXVB.FMGetNemonicoMoneda(TransactionExecutor1.FMGetVariableGlobal("VGMoneda")) TransactionExecutor1.FMGetObjeto("VL_NEM_DOL").valor
		 * = VLMco.ATXVB.FMGetNemonicoMoneda(TransactionExecutor1.FMGetObjeto("TXTMONEDA").Text) VLMco.Generales.FMCasualCustomer(parMonto, parMoneda,
		 * parTransaccion, parCtaBco,parCodTitular,parInfTran) If VLMco.Generales.VGCodGestor <> "" Then parRetornocc=True Else parRetornocc=False End
		 * If TransactionExecutor1.FMGetObjeto("VLRETORNO").valor = CStr(parRetornocc) 'TransactionExecutor1.FMGetObjeto("VLCODENTE").valor =
		 * TransactionExecutor1.FMGetVariableGlobal("VLCodEnte") TransactionExecutor1.FMGetObjeto("VLCODENTE").valor = VLMco.Generales.VGCodGestor '
		 * TransactionExecutor1.FMGetObjeto("VLGESTOR").valor = VLMco.Generales.VGGestor TransactionExecutor1.FMGetObjeto("VLTIPOID").valor =
		 * VLMco.Generales.VGTipoIDCC TransactionExecutor1.FMGetObjeto("VLCEDULA").valor = VLMco.Generales.VGCedula
		 * TransactionExecutor1.FMGetObjeto("VLNOMBRE").valor = VLMco.Generales.VGPrimerNombreCC +" "+ VLMco.Generales.VGSegundoNombreCC +" "+
		 * VLMco.Generales.VGApellidoPaternoCC +" "+ VLMco.Generales.VGApellidoMaternoCC 'TransactionExecutor1.FMGetObjeto("VLNOMBRE").valor =
		 * VLMco.Generales.VGPrimerNombreCC +" "+ VLMco.Generales.VGApellidoPaternoCC +" "+ VLMco.Generales.VGApellidoMaternoCC
		 * TransactionExecutor1.FMGetObjeto("VLDIRECCION").valor = VLMco.Generales.VGDireccionCC TransactionExecutor1.FMGetObjeto("VLTELEFONO").valor
		 * = VLMco.Generales.VGTelefono TransactionExecutor1.FMGetObjeto("VLORIGEN").valor = VLMco.Generales.VGOrigen
		 * TransactionExecutor1.FMGetObjeto("VLDESTINO").valor = VLMco.Generales.VGDestino TransactionExecutor1.FMGetObjeto("VLMOTIVO").valor =
		 * VLMco.Generales.VGMotivo TransactionExecutor1.FMGetObjeto("VLCODOCACIONAL").valor = VLMco.Generales.VGCodEnte
		 * TransactionExecutor1.FMGetObjeto("TXTCODOCACIONAL").Text=VLMco.Generales.VGCodEnte
		 * 
		 * 
		 * End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_27()
	 */
	@Override
	public void FUNCION_27() {
		/*
		 * If parEtiqueta = "FUNCION_27" Then
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_28()
	 */
	@Override
	public void FUNCION_28() {
		/*
		 * If TransactionExecutor1.FMGetObjeto("VLRETORNO").valor = "True" Then parValorRetorno=True Else parValorRetorno=False End If End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_29()
	 */
	@Override
	public void FUNCION_29() {
		/*
		 * If parEtiqueta = "FUNCION_29" Then
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#ACCIONSALIR()
	 */
	@Override
	public void ACCIONSALIR() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_2()
	 */
	@Override
	public void FUNCION_2() {
		if (getVarAUTORIZA() == null || getVarAUTORIZA().equals("N")) {
			cancelAction(Constants.SUPERVISOR);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_3()
	 */
	@Override
	public void FUNCION_3() {
		/*
		 * TransactionExecutor1.FMGetObjeto("autoriza").valor = TransactionExecutor1.FMGetObjeto("autoriza").valor
		 */
		if (getTXTTOTDEPOSITO() != null && getTXTTOTDEPOSITO().compareTo(new BigDecimal(0)) == 0) {
			failAction("FUNCION_3");
			addErrorMessage(Constants.MENSSAGE_VALUE_ZERO, 999);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_4()
	 */
	@Override
	public void FUNCION_4() {
		/*
		 * ' Codigo de BEGIN CODE VB Dim EjecucionMasivo As Integer = 1 'If VLCco.get_MCO("COBISmcoATXMas.clsMasivas").VGTipoEjecModoMas =
		 * EjecucionMasivo Then '
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_5()
	 */
	@Override
	public void FUNCION_5() {
		/*
		 * If TransactionExecutor1.FMGetObjeto("VLCancelar").valor = "S" Then parValorRetorno = True Else parValorRetorno = False End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_6()
	 */
	@Override
	public void FUNCION_6() {
		/*
		 * 'System.Diagnostics.Debugger.Break()
		 * 
		 * ' Codigo de BEGIN CODE VB
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_7()
	 */
	@Override
	public void FUNCION_7() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGModo") = "N-ON" Or TransactionExecutor1.FMGetVariableGlobal("VGModo") = "N-OFF" Then
		 * parValorRetorno = True Else parValorRetorno = False End If
		 */
		cancelAction("sp_datos_cli_branch");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_8()
	 */
	@Override
	public void FUNCION_8() {
		/*
		 * ' Codigo de BEGIN CODE VB If TransactionExecutor1.FMGetObjeto("i_num_denom").valor = 0 Then Dim lngCt As Long Dim VTContador As Integer Dim
		 * VTContador_tmp As Integer VTContador = 0 VTContador_tmp = 0 Dim VTParm As String VTParm = "" 'Recuperando los datos desde la sumadora For
		 * lngCt = 1 To VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX").SumDenominaciones(VLCco.VGAmbienteActivo).ContarDenominaciones
		 * With VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX").SumDenominaciones(VLCco.VGAmbienteActivo).Denominaciones(lngCt) VTParm =
		 * .Titulo + "&" + Str(.ValorUnitario) + "&" + LTrim(RTrim(Str(.Cantidad))) + "&" + Str(.valor) VTContador = VTContador + 1 'Pcoello se mandan
		 * las denominaciones de dos en dos en parametros de 64 If lngCt Mod 2 <> 0 Then VTContador_tmp = VTContador_tmp + 1
		 * TransactionExecutor1.FMGetObjeto("i_denom" + CStr(VTContador_tmp)).valor = VTParm Else TransactionExecutor1.FMGetObjeto("i_denom" +
		 * CStr(VTContador_tmp)).valor = TransactionExecutor1.FMGetObjeto("i_denom" + CStr(VTContador_tmp)).valor + "@" + VTParm End If End With Next
		 * TransactionExecutor1.FMGetObjeto("i_num_denom").valor = VTContador End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_9()
	 */
	@Override
	public void FUNCION_9() {
		/*
		 * ' Codigo de BEGIN CODE VB
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_10()
	 */
	@Override
	public void FUNCION_10() {
		/*
		 * ' Codigo de BEGIN CODE VB If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") = "" Then End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_49()
	 */
	@Override
	public void FUNCION_49() {
		/*
		 * 
		 * 
		 * VLMco = VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX") If TransactionExecutor1.FMGetObjeto("txtChqPropios").text <> 0 Or
		 * TransactionExecutor1.FMGetObjeto("txtChqLocal").text <> 0 Or TransactionExecutor1.FMGetObjeto("txtChqOtrasP").text <> 0 Then '
		 * TransactionExecutor1.FMGetObjeto("VLMSG").valor = VLMco.ATXVB.FMGetParametro("MCHQ", "ATX","C")
		 * TransactionExecutor1.FMGetObjeto("VLMSG").valor = "CHEQUES Y SU TIPO DE CAMBIO SUJETOS A EFECTIVIZACION" End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_22()
	 */
	@Override
	public void FUNCION_22() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then parValorRetorno=True Else parValorRetorno=False End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_23()
	 */
	@Override
	public void FUNCION_23() {
		/*
		 * Dim parSSNBranch As String Dim parCodigoCliente As Integer Dim parTipoIE As String Dim parNumeroOperacion As String Dim parMoneda As
		 * Integer Dim parMonto As String Dim parProducto As Integer Dim parCuenta As String Dim parTransaccion As Integer Dim parTipoGenera As String
		 * Dim parFormaOp As String Dim parCtaBco As String Dim parMonedaOrigen As Integer Dim parCuentaOrigen As String Dim parMonedaDestino As
		 * Integer Dim parCuentaDestino As String Dim parMonedaCuenta As Integer
		 * 
		 * If TransactionExecutor1.FMGetObjeto("VLGENERAFORM").valor ="S" Then
		 * parMonto=Convert.ToDecimal(TransactionExecutor1.FMGetObjeto("txtTotDeposito").text, System.Globalization.CultureInfo.InvariantCulture)
		 * parMoneda = TransactionExecutor1.FMGetObjeto("TXTMONEDA").text parSSNBranch = TransactionExecutor1.FMGetObjeto("VLSSN").valor
		 * parCodigoCliente = TransactionExecutor1.FMGetObjeto("VLCODENTE").valor parCtaBco =
		 * TransactionExecutor1.FMGetObjeto("txtNumCuenta").Cliptext parTipoIE = "I" parProducto = 4 parTransaccion = 252
		 * 
		 * parTipoGenera = TransactionExecutor1.FMGetObjeto("VLTIPOGENERA").valor parFormaOp = TransactionExecutor1.FMGetObjeto("VL_FORMA_OP").valor
		 * VLMco = VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX") parMonedaCuenta = TransactionExecutor1.FMGetVariableGlobal("VGMONEDA")
		 * 
		 * VLMco.Generales.FMFormularioUIf(parSSNBranch,parCodigoCliente,
		 * parTipoIE,parTransaccion,parMoneda,parMonto,parProducto,parCtaBco,parTipoGenera, 0,"", parMonedaCuenta,parCtaBco, 0, 0, parFormaOp, "",
		 * "","") End If End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_24()
	 */
	@Override
	public void FUNCION_24() {
		/*
		 * End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_60()
	 */
	@Override
	public void FUNCION_60() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") = "REV" Then parValorRetorno=True Else parValorRetorno=False End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_61()
	 */
	@Override
	public void FUNCION_61() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_11()
	 */
	@Override
	public void FUNCION_11() {
		/*
		 * VLMco = VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX") TransactionExecutor1.FMGetObjeto("VL_COPIA_COMP").valor =
		 * VLMco.ATXVB.FMGetParametro("ICPCOM", "ATX","C")
		 * 
		 * If TransactionExecutor1.FMGetObjeto("TXTMONEDA").text <> TransactionExecutor1.FMGetVariableGlobal("VGMONEDA") Then
		 * TransactionExecutor1.FMGetObjeto("VL_TIPO_IMP_COMP").valor = "S" parValorRetorno = True Else
		 * TransactionExecutor1.FMGetObjeto("VL_TIPO_IMP_COMP").valor = "N" parValorRetorno = False End If
		 */
		if (!getTXTMONEDA().getKey().equals(getContextCurrency().getKey())) {
			setVarVL_TIPO_IMP_COMP("S");
			// TODO: CANCELA O ACTIVA FUNCION
		} else {
			setVarVL_TIPO_IMP_COMP("N");
			// TODO: CANCELA O ACTIVA FUNCION
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_12()
	 */
	@Override
	public void FUNCION_12() {
		/*
		 * 
		 * If TransactionExecutor1.FMGetObjeto("VL_TIPO_IMP_COMP").valor = "S" And TransactionExecutor1.FMGetObjeto("VL_COPIA_COMP").valor = "S" And
		 * TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then parValorRetorno = True Else parValorRetorno = False End If
		 */
		if (getVarVL_TIPO_IMP_COMP().equals('S') && getVarVL_COPIA_COMP().equals('S') && !getContext().getReverseMode()) {
			// TODO: CANCELA O ACTIVA FUNCION
		} else {
			// TODO: CANCELA O ACTIVA FUNCION
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_56()
	 */
	@Override
	public void FUNCION_56() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_14()
	 */
	@Override
	public void FUNCION_14() {
		/*
		 * 
		 * If TransactionExecutor1.FMGetObjeto("VL_TIPO_IMP_COMP").valor = "N" And TransactionExecutor1.FMGetObjeto("VL_COPIA_COMP").valor = "S" And
		 * TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then parValorRetorno = True Else parValorRetorno = False End If
		 */
		if (getVarVL_TIPO_IMP_COMP().equals('N') && getVarVL_COPIA_COMP().equals('S') && !getContext().getReverseMode()) {
			// TODO: CANCELA O ACTIVA FUNCION
		} else {
			// TODO: CANCELA O ACTIVA FUNCION
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_57()
	 */
	@Override
	public void FUNCION_57() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_53()
	 */
	@Override
	public void FUNCION_53() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" And TransactionExecutor1.FMGetObjeto("VLGENERO_FAC").valor ="S" Then 'If
		 * TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" And TransactionExecutor1.FMGetObjeto("TXTFACTURA").text ="S" Then
		 * parValorRetorno = True ' TransactionExecutor1.FMGetObjeto("VLSEC_FACT").valor = TransactionExecutor1.FMGetObjeto("TXTFACTURA").text
		 * TransactionExecutor1.FMGetObjeto("VLMSGFAC").valor =
		 * "ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS. EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LA LEY" Else parValorRetorno = False
		 * End If
		 */
		if (!getContext().getReverseMode() && getVarVLGENERO_FAC().equals('S')) {
			// TODO: CANCELA O ACTIVA FUNCION
			setVarVLMSGFAC("ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS. EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LA LEY");
		} else {
			// TODO: CANCELA O ACTIVA FUNCION
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#ACCIONIMPRIMIRFACTURA()
	 */
	@Override
	public void ACCIONIMPRIMIRFACTURA() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_54()
	 */
	@Override
	public void FUNCION_54() {
		/*
		 * '' NADA
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_58()
	 */
	@Override
	public void FUNCION_58() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" And TransactionExecutor1.FMGetObjeto("VLGENFACCV").valor = "S" Then
		 * parValorRetorno = True TransactionExecutor1.FMGetObjeto("VLMSGFAC").valor =
		 * "ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS. EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LA LEY"
		 * TransactionExecutor1.FMGetObjeto("VLTIPOFAC").valor = "C" Else parValorRetorno = False End If
		 */
		if (!getContext().getReverseMode() && getVarVLGENFACCV() != null && getVarVLGENFACCV().equals('S')) {
			// TODO: CANCELA O ACTIVA FUNCION
			setVarVLMSGFAC("ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS. EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LA LEY");
			setVarVLTIPOFAC("C");
		} else {
			// TODO: CANCELA O ACTIVA FUNCION
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_59()
	 */
	@Override
	public void FUNCION_59() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_39()
	 */
	@Override
	public void FUNCION_39() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") = "REV" Then
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_13()
	 */
	@Override
	public void FUNCION_13() {
		/*
		 * ' Codigo de BEGIN CODE VB If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then
		 * VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX").SumDenominaciones(VLCco.VGAmbienteActivo).EliminarDenominaciones()
		 * TransactionExecutor1.FMGetObjeto("txtEfectivo").Enabled = True TransactionExecutor1.FMGetObjeto("i_num_denom").valor = 0
		 */
		setVarVLCEDULA(getNumberDocumentOccasionalClient());
		setVarVLEXISTECE(getTypeClientOccasionalClient());
		setVarVLCODOCACIONAL(getCodeEnteOccasionalClient());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_47()
	 */
	@Override
	public void FUNCION_47() {
		/*
		 * TransactionExecutor1.FMGetObjeto("VLNUMCTRL").valor = 0 TransactionExecutor1.FMGetObjeto("VLTIPOCHQ").valor = "0"
		 */
		setVarVLNUMCTRL(0);
		setVarVLTIPOCHQ('0');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_40()
	 */
	@Override
	public void FUNCION_40() {
		/*
		 * 
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") = "REV" Then
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_48()
	 */
	@Override
	public void FUNCION_48() {
		/*
		 * TransactionExecutor1.FMGetObjeto("VLNUMCTRL").valor = 0 TransactionExecutor1.FMGetObjeto("VLTIPOCHQ").valor = "0"
		 */
		setVarVLNUMCTRL(0);
		setVarVLTIPOCHQ('0');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_15()
	 */
	@Override
	public void FUNCION_15() {
		/*
		 * ' Codigo de BEGIN CODE VB TransactionExecutor1.FMGetObjeto("VLConsultaNombre").valor = "N" If
		 * TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") = "" Then If Trim$(TransactionExecutor1.FMGetObjeto("txtNumCuenta").Cliptext) <> ""
		 * Then TransactionExecutor1.FMGetObjeto("VLConsultaNombre").valor = "S" End If End If End If
		 */
		setVarVLCONSULTANOMBRE("N");
		if (getVarVGREVERSO().equals("")) {
			if (!getTXTNUMCUENTA().trim().equals("")) {
				setVarVLCONSULTANOMBRE("S");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_16()
	 */
	@Override
	public void FUNCION_16() {
		/*
		 * If TransactionExecutor1.FMGetObjeto("VLConsultaNombre").valor = "S" Then parValorRetorno = True Else parValorRetorno = False End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_17()
	 */
	@Override
	public void FUNCION_17() {
		/*
		 * TransactionExecutor1.FMGetObjeto("VLConsultaNombre").valor = TransactionExecutor1.FMGetObjeto("VLConsultaNombre").valor
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_45()
	 */
	@Override
	public void FUNCION_45() {
		/*
		 * Dim pTipo As String Dim pCtrl As Integer Dim pCantidad As Short Dim pValorP As Double Dim pValorL As Double Dim pValorE As Double Dim
		 * pValorPConv As Double Dim pValorLConv As Double Dim pMoneda As String Dim pModulo As String
		 * 
		 * VLMco = VLCco.get_MCO("COBISCorp.tCOBIS.Branch.COBISMCOATX.mcoATX") TransactionExecutor1.FMGetObjeto("VLDETCHQ").valor =
		 * VLMco.ATXVB.FMGetParametro("DETCHQ", "REM","C")
		 * 
		 * pTipo = TransactionExecutor1.FMGetObjeto("VLTIPOCHQ").valor pCtrl = TransactionExecutor1.FMGetObjeto("VLNUMCTRL").valor
		 * 
		 * pMoneda = TransactionExecutor1.FMGetObjeto("TXTMONEDA").text pModulo = TransactionExecutor1.FMGetObjeto("CONST_MODULO").valor
		 * 
		 * If TransactionExecutor1.FMGetObjeto("VLDETCHQ").valor = "S" And pTipo <> "0" Then
		 * 
		 * ' pCantidad = VLMco.ATXVB.FMDetalleCheques(252, pTipo, pCtrl, pValorP, pValorL, pValorE) pCantidad = VLMco.ATXVB.FMDetalleCheques(252,
		 * pTipo, pCtrl, pValorP, pValorL, pValorE, pValorPConv, pValorLConv, pMoneda, pModulo)
		 * 
		 * TransactionExecutor1.FMGetObjeto("VLNUMCTRL").valor = pCtrl TransactionExecutor1.FMGetObjeto("TXTCHQPROPIOS").text = Format(pValorP,
		 * "#,##0.00") TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").text = Format(pValorL, "#,##0.00")
		 * TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").text = Format(pValorE, "#,##0.00")
		 * 
		 * TransactionExecutor1.FMGetObjeto("MKMONRECPROP").text = Format(pValorPConv, "#,##0.00")
		 * TransactionExecutor1.FMGetObjeto("MKMONRECLOC").text = Format(pValorLConv, "#,##0.00")
		 * 
		 * TransactionExecutor1.FMGetObjeto("VLTIPOCHQ").valor = "0"
		 * 
		 * End If
		 * 
		 * If pValorP > 0 Then TransactionExecutor1.FMGetObjeto("TXTCHQPROPIOS").SetFocus() TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").SetFocus()
		 * End If
		 * 
		 * If pValorL > 0 Then TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").SetFocus() TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").SetFocus()
		 * End If
		 * 
		 * If pValorE > 0 Then TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").SetFocus()
		 * TransactionExecutor1.FMGetObjeto("TXTREFERENCIA").SetFocus() End If
		 */
		// TODO: No existe parametros para la transaccion
		Character pTipo = getVarVLTIPOCHQ();
		Long pCtrl = getVarVLNUMCTRL();
		// String pMoneda = getTXTMONEDA().getKey();
		// String pModulo = getVarCONST_MODULO();
		if (getVarVLDETCHQ().compareTo('S') == 0 && pTipo.compareTo('0') == 0) {
			setVarVLNUMCTRL(pCtrl);
			setVarVLTIPOCHQ('0');
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_18()
	 */
	public void FUNCION_18() {
		/*
		 * If parEtiqueta = "FUNCION_18" Then
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_19()
	 */
	public void FUNCION_19() {
		/*
		 * If parEtiqueta = "FUNCION_19" Then
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_20()
	 */
	public void FUNCION_20() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then parValorRetorno = True Else parValorRetorno = False End If
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_21()
	 */
	public void FUNCION_21() {
		addDenominationDetail();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_34()
	 */
	public void FUNCION_34() {
		/*
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" And TransactionExecutor1.FMGetObjeto("TXTMONEDA").text <>
		 * TransactionExecutor1.FMGetVariableGlobal("VGMONEDA") Then parValorRetorno = True
		 * 
		 * Else parValorRetorno = False End If
		 */
		if (!getContext().getReverseMode() && !getTXTMONEDA().getKey().equals(getContextCurrency().getKey())) {
			enabledAction("sp_op_divisas_cascara");
		} else {
			cancelAction("sp_op_divisas_cascara");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_50()
	 */
	public void FUNCION_50() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_35()
	 */
	public void FUNCION_35() {
		/*
		 * Dim pTotal As Double Dim pLocal As Double Dim pEfectivo As Double Dim pPropios As Double
		 * 
		 * pTotal = 0 pEfectivo = TransactionExecutor1.FMGetObjeto("MKMONTORECIBIR").text pPropios =
		 * TransactionExecutor1.FMGetObjeto("MKMONRECPROP").text pLocal = TransactionExecutor1.FMGetObjeto("MKMONRECLOC").text pTotal = pEfectivo +
		 * pLocal + pPropios
		 * 
		 * TransactionExecutor1.FMGetObjeto("LBLTOTCONV").text = Format(pTotal, "#,##0.00")
		 */
		BigDecimal pTotal;
		BigDecimal pEfectivo = (getMKMONTORECIBIR() == null) ? BigDecimal.ZERO : getMKMONTORECIBIR();
		BigDecimal pPropios = (getMKMONRECPROP() == null) ? BigDecimal.ZERO : getMKMONRECPROP();
		BigDecimal pLocal = (getMKMONRECLOC() == null) ? BigDecimal.ZERO : getMKMONRECLOC();
		pTotal = pEfectivo.add(pPropios).add(pLocal);
		setFieldValue(FIELD_LBLTOTCONV, pTotal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_41()
	 */
	public void FUNCION_41() {
		/*
		 * If TransactionExecutor1.FMGetObjeto("TXTMONEDA").text <> TransactionExecutor1.FMGetVariableGlobal("VGMONEDA") Then If
		 * TransactionExecutor1.FMGetObjeto("MKMONTORECIBIR").text = "0.00" Then TransactionExecutor1.FMGetObjeto("TXTEFECTIVO").text = "0.00"
		 * TransactionExecutor1.FMGetObjeto("TXTTOTDEPOSITO").text = "0.00" 'ECO TransactionExecutor1.FMGetObjeto("TXTMONEDA").text =
		 * TransactionExecutor1.FMGetVariableGlobal("VGMONEDA") 'ECOTransactionExecutor1.FMGetObjeto("TXTMONEDA").Setfocus() parValorRetorno = True
		 * End If Else parValorRetorno = False
		 * 
		 * End If
		 */
		if (getTXTMONEDA().equals(getVarVGMONEDA())) {
			if (getMKMONTORECIBIR().compareTo(BigDecimal.ZERO) == 0) {
				setFieldValue(FIELD_TXTEFECTIVO, BigDecimal.ZERO);
				setFieldValue(FIELD_TXTTOTDEPOSITO, BigDecimal.ZERO);
				// TODO: CANCELAR - HABILITAR ACCION
			}
		} else {
			// TODO: CANCELAR - HABILITAR ACCION
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_42()
	 */
	public void FUNCION_42() {
		/*
		 * Dim pTotal As Double Dim pLocal As Double Dim pEfectivo As Double Dim pPropios As Double
		 * 
		 * pTotal = 0 pEfectivo = TransactionExecutor1.FMGetObjeto("MKMONTORECIBIR").text pPropios =
		 * TransactionExecutor1.FMGetObjeto("MKMONRECPROP").text pLocal = TransactionExecutor1.FMGetObjeto("MKMONRECLOC").text pTotal = pEfectivo +
		 * pLocal + pPropios
		 * 
		 * TransactionExecutor1.FMGetObjeto("LBLTOTCONV").text = Format(pTotal, "#,##0.00") TransactionExecutor1.FMGetObjeto("VLTIPOCHQ").valor = "3"
		 */
		BigDecimal pTotal;
		BigDecimal pEfectivo = (getMKMONTORECIBIR() == null) ? BigDecimal.ZERO : getMKMONTORECIBIR();
		BigDecimal pPropios = (getMKMONRECPROP() == null) ? BigDecimal.ZERO : getMKMONRECPROP();
		BigDecimal pLocal = (getMKMONRECLOC() == null) ? BigDecimal.ZERO : getMKMONRECLOC();
		pTotal = pEfectivo.add(pPropios).add(pLocal);
		setFieldValue(FIELD_LBLTOTCONV, pTotal);
		setVarVLTIPOCHQ('3');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_43()
	 */
	public void FUNCION_43() {

		/*
		 * BigDecimal pTotal; BigDecimal pEfectivo = (getMKMONTORECIBIR() == null) ? BigDecimal.ZERO : getMKMONTORECIBIR(); //BigDecimal pPropios =
		 * (getMKMONRECPROP() == null) ? BigDecimal.ZERO : getMKMONRECPROP(); BigDecimal pLocal = (getMKMONRECLOC() == null) ? BigDecimal.ZERO :
		 * getMKMONRECLOC(); //pTotal = pEfectivo.add(pPropios).add(pLocal); pTotal = pEfectivo.add(pLocal); setFieldValue(FIELD_LBLTOTCONV, pTotal);
		 */
		setVarVLTIPOCHQ('1');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_44()
	 */
	public void FUNCION_44() {
		/*
		 * TransactionExecutor1.FMGetObjeto("VLTIPOCHQ").valor = "2"
		 */
		setVarVLTIPOCHQ('2');
		/*if(getTXTNUMCUENTA() == null || "".equals(getTXTNUMCUENTA())) {
			cancelAction("popupVariablesDetailsCheckOwner");
			cancelAction(Constants.NAME_RETURN_DETAILS_CHECK);
			cancelAction(Constants.NAME_ACTION_CLIENT_DETAILS_CHECK);
		}*/
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobiscorp.cobis.finantial.engine.services.impl.FCA41Gen#FUNCION_51()
	 */
	public void FUNCION_51() {
		/*
		 * 
		 * 
		 * If TransactionExecutor1.FMGetVariableGlobal("VGREVERSO") <> "REV" Then
		 * 
		 * If TransactionExecutor1.FMGetObjeto("TXTMONEDA").text = TransactionExecutor1.FMGetVariableGlobal("VGMONEDA") Then
		 * TransactionExecutor1.FMGetObjeto("MKMONTORECIBIR").text="" TransactionExecutor1.FMGetObjeto("MKMONRECPROP").text=""
		 * TransactionExecutor1.FMGetObjeto("MKMONRECLOC").text="" TransactionExecutor1.FMGetObjeto("LBLTOTCONV").text=""
		 * TransactionExecutor1.FMGetObjeto("TXTCHQPROPIOS").text="" TransactionExecutor1.FMGetObjeto("TXTCHQPROPIOS").Enabled = True
		 * TransactionExecutor1.FMGetObjeto("MKTASA").text="" TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").text=""
		 * TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").Enabled = True TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").text=""
		 * TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").Enabled =True TransactionExecutor1.FMGetObjeto("TXTEFECTIVO").Setfocus()
		 * 
		 * Else ' TransactionExecutor1.FMGetObjeto("TXTCHQPROPIOS").text="" ' TransactionExecutor1.FMGetObjeto("TXTCHQPROPIOS").Enabled = False
		 * 
		 * 'TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").text="" 'TransactionExecutor1.FMGetObjeto("TXTCHQLOCAL").Enabled = False
		 * TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").text="" TransactionExecutor1.FMGetObjeto("TXTCHQOTRASP").Enabled = False
		 * 
		 * TransactionExecutor1.FMGetObjeto("TXTEFECTIVO").Setfocus() End If
		 * 
		 * End If ' Fin <> reverso
		 */
		if (!getContext().getReverseMode()) {
			if (getTXTMONEDA().getKey().equals(getContextCurrency().getKey())) {
				setFieldValue(FIELD_MKMONTORECIBIR, BigDecimal.ZERO);
				setFieldValue(FIELD_MKMONRECPROP, BigDecimal.ZERO);
				setFieldValue(FIELD_MKMONRECLOC, BigDecimal.ZERO);
				setFieldValue(FIELD_LBLTOTCONV, BigDecimal.ZERO);
				setFieldValue(FIELD_MKTASA, BigDecimal.ZERO);
				setFieldValue(FIELD_TXTCHQLOCAL, BigDecimal.ZERO);
				Attribute fieldTXTCHQLOCAL = this.getAttribute(FIELD_TXTCHQLOCAL);
				fieldTXTCHQLOCAL.setEnabled(Boolean.TRUE);
				setFieldValue(FIELD_TXTCHQOTRASP, BigDecimal.ZERO);
				Attribute fieldTXTCHQOTRASP = this.getAttribute(FIELD_TXTCHQOTRASP);
				fieldTXTCHQOTRASP.setEnabled(Boolean.TRUE);
				setFieldValue(FIELD_TXTTOTDEPOSITO, BigDecimal.ZERO);
			} else {
				setFieldValue(FIELD_TXTCHQOTRASP, BigDecimal.ZERO);
				Attribute fieldTXTCHQOTRASP = this.getAttribute(FIELD_TXTCHQOTRASP);
				fieldTXTCHQOTRASP.setEnabled(Boolean.FALSE);
			}
		}
	}

}
