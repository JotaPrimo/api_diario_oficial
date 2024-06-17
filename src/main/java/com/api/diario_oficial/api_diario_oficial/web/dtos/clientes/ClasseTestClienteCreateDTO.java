package com.api.diario_oficial.api_diario_oficial.web.dtos.clientes;

import com.api.diario_oficial.api_diario_oficial.entity.Usuario;
import com.api.diario_oficial.api_diario_oficial.validation.custom.ConditionalNotNull;
import com.api.diario_oficial.api_diario_oficial.validation.custom.ExistsInDatabase;
import com.api.diario_oficial.api_diario_oficial.validation.custom.RequiredInInt;
import com.api.diario_oficial.api_diario_oficial.validation.custom.RequiredInString;

@ConditionalNotNull.List({
        @ConditionalNotNull(
                field = "doencaId",
                dependsOnField = "possuiDoenca",
                dependsOnFieldValue = "SIM",
                message = "doencaId deve ser preenchido quando possuiDoenca for SIM"
        ),
        @ConditionalNotNull(
                field = "outroCampo",
                dependsOnField = "algumaOutraCondicao",
                dependsOnFieldValue = "OUTRO_VALOR",
                message = "outroCampo deve ser preenchido quando algumaOutraCondicao for OUTRO_VALOR"
        )
})
public class ClasseTestClienteCreateDTO {

    @RequiredInString(values = {"SIM", "NAO"}, message = "possuiDoenca deve ser 'SIM' ou 'NAO'")
    private String possuiDoenca;

    @RequiredInInt(values = {1, 2, 3}, message = "doencaId deve ser um dos valores permitidos: 1, 2, 3")
    @ExistsInDatabase(entityClass = Usuario.class, message = "doencaId não existe no banco de dados")
    private Long doencaId;

    private String algumaOutraCondicao;
    private String outroCampo;

    // getters e setters

    public String getPossuiDoenca() {
        return possuiDoenca;
    }

    public void setPossuiDoenca(String possuiDoenca) {
        this.possuiDoenca = possuiDoenca;
    }

    public Long getDoencaId() {
        return doencaId;
    }

    public void setDoencaId(Long doencaId) {
        this.doencaId = doencaId;
    }

    public String getAlgumaOutraCondicao() {
        return algumaOutraCondicao;
    }

    public void setAlgumaOutraCondicao(String algumaOutraCondicao) {
        this.algumaOutraCondicao = algumaOutraCondicao;
    }

    public String getOutroCampo() {
        return outroCampo;
    }

    public void setOutroCampo(String outroCampo) {
        this.outroCampo = outroCampo;
    }

}
