package ve.com.tracking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ve.com.tracking.model.Authorities;
import ve.com.tracking.model.Authoritys;
import ve.com.tracking.model.CategoriaDetalle;
import ve.com.tracking.model.Countries;
import ve.com.tracking.model.Destinos;
import ve.com.tracking.model.EmpresaEnvio;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.TipoEmbalaje;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.AuthoritiesRepository;
import ve.com.tracking.repository.CategoriaDetalleRepository;
import ve.com.tracking.repository.CountriesRepository;
import ve.com.tracking.repository.EmpresaEnvioRepository;
import ve.com.tracking.repository.TipoEmbalajeRepository;
import ve.com.tracking.services.AuthorityService;
import ve.com.tracking.services.DestinosService;
import ve.com.tracking.services.PaqueteService;
import ve.com.tracking.services.TipoCambioService;
import ve.com.tracking.services.UsersService;

@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean{
    @SuppressWarnings("deprecation")
	@Override
    protected void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
        // Register application converters and formatters
    }

	@Autowired
    AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	EmpresaEnvioRepository empresaEnvioRepository;

	@Autowired
    AuthorityService authorityService;

	@Autowired
    UsersService usersService;
	
	@Autowired
	TipoCambioService tipoCambioService;
	
	@Autowired
	CountriesRepository countriesRepository;

	@Autowired
	PaqueteService paqueteService;
	
	@Autowired
	DestinosService destinosService;
	
	@Autowired
	TipoEmbalajeRepository tipoEmbalajeRepository;
	
	@Autowired
	CategoriaDetalleRepository categoriaDetalleRepository;
	
	public Converter<String, Countries> getIdToCountriesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.Countries>() {
            public ve.com.tracking.model.Countries convert(java.lang.String code) {
                return countriesRepository.findCountriesByIsoAlpha2(code);
            }
        };
    }	
	
	public Converter<Countries, String> getCountriesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.Countries, java.lang.String>() {
            public String convert(Countries countries) {
                return new StringBuilder().append(countries.getName()).toString();
            }
        };
    }
	
	public Converter<Authorities, String> getAuthoritiesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.Authorities, java.lang.String>() {
            public String convert(Authorities authorities) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<Long, Authorities> getIdToAuthoritiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.Authorities>() {
            public ve.com.tracking.model.Authorities convert(java.lang.Long id) {
                return authoritiesRepository.findOne(id);
            }
        };
    }

	public Converter<String, Authorities> getStringToAuthoritiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.Authorities>() {
            public ve.com.tracking.model.Authorities convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Authorities.class);
            }
        };
    }

	public Converter<Authoritys, String> getAuthoritysToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.Authoritys, java.lang.String>() {
            public String convert(Authoritys authoritys) {
                return new StringBuilder().append(authoritys.getAuthority()).append(" - ").append(authoritys.getDescripcion()).toString();
            }
        };
    }

	public Converter<Long, Authoritys> getIdToAuthoritysConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.Authoritys>() {
            public ve.com.tracking.model.Authoritys convert(java.lang.Long id) {
                return authorityService.findAuthoritys(id);
            }
        };
    }

	public Converter<String, Authoritys> getStringToAuthoritysConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.Authoritys>() {
            public ve.com.tracking.model.Authoritys convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Authoritys.class);
            }
        };
    }

	public Converter<Users, String> getUsersToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.Users, java.lang.String>() {
            public String convert(Users users) {
                return new StringBuilder().append(users.getUsername()).append(' ').append(' ').append(users.getFirstName()).append(' ').append(users.getLastName()).toString();
            }
        };
    }

	public Converter<Long, Users> getIdToUsersConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.Users>() {
            public ve.com.tracking.model.Users convert(java.lang.Long id) {
                return usersService.findUsers(id);
            }
        };
    }

	public Converter<Long, EmpresaEnvio> getIdToEmpresaEnvioConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.EmpresaEnvio>() {
            public ve.com.tracking.model.EmpresaEnvio convert(java.lang.Long id) {
                return empresaEnvioRepository.findOne(id);
            }
        };
    }	
	public Converter<String, Users> getStringToUsersConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.Users>() {
            public ve.com.tracking.model.Users convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Users.class);
            }
        };
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

	public Converter<EmpresaEnvio, String> getEmpresaEnvioToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.EmpresaEnvio, java.lang.String>() {
            public String convert(EmpresaEnvio empresaEnvio) {
                return new StringBuilder().append(empresaEnvio.getName()).toString();
            }
        };
    }

	public Converter<String, EmpresaEnvio> getStringToEmpresaEnvioConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.EmpresaEnvio>() {
            public ve.com.tracking.model.EmpresaEnvio convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), EmpresaEnvio.class);
            }
        };
    }
	
	//paquete
	public Converter<String, Paquete> getStringToPaqueteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.Paquete>() {
            public ve.com.tracking.model.Paquete convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Paquete.class);
            }
        };
    }
	
	public Converter<Long, Paquete> getIdToPaqueteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.Paquete>() {
            public ve.com.tracking.model.Paquete convert(java.lang.Long id) {
                return paqueteService.findPaquete(id);
            }
        };
    }
	public Converter<Paquete, String> getPaqueteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.Paquete, java.lang.String>() {
            public String convert(Paquete paquete) {
                return new StringBuilder().append(paquete.getTracking()).toString();
            }
        };
    }
	
	//destinos
	public Converter<String, Destinos> getStringToDestinosConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.Destinos>() {
            public ve.com.tracking.model.Destinos convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Destinos.class);
            }
        };
    }
	
	public Converter<Long, Destinos> getIdToDestinosConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.Destinos>() {
            public ve.com.tracking.model.Destinos convert(java.lang.Long id) {
                return destinosService.findDestinos(id);
            }
        };
    }
	public Converter<Destinos, String> getDestinosToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.Destinos, java.lang.String>() {
            public String convert(Destinos destino) {
                return new StringBuilder().append(destino.getPais().getName()).append("-").append(destino.getCiudad()).toString();
            }
        };
    }
	
	//tipo embalajes
	public Converter<String, TipoEmbalaje> getStringToTipoEmbalajeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.TipoEmbalaje>() {
            public ve.com.tracking.model.TipoEmbalaje convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), TipoEmbalaje.class);
            }
        };
    }
	
	public Converter<Long, TipoEmbalaje> getIdToTipoEmbalajeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.TipoEmbalaje>() {
            public ve.com.tracking.model.TipoEmbalaje convert(java.lang.Long id) {
                return tipoEmbalajeRepository.findOne(id);
            }
        };
    }
	
	public Converter<TipoEmbalaje, String> getTipoEmbalajeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.TipoEmbalaje, java.lang.String>() {
            public String convert(TipoEmbalaje tipoEmbalaje) {
                return new StringBuilder().append(tipoEmbalaje.getNombre()).toString();
            }
        };
    }	
	
	//categoria detalle
	public Converter<String, CategoriaDetalle> getStringToCategoriaDetalleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ve.com.tracking.model.CategoriaDetalle>() {
            public ve.com.tracking.model.CategoriaDetalle convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CategoriaDetalle.class);
            }
        };
    }
	
	public Converter<Long, CategoriaDetalle> getIdToCategoriaDetalleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ve.com.tracking.model.CategoriaDetalle>() {
            public ve.com.tracking.model.CategoriaDetalle convert(java.lang.Long id) {
                return categoriaDetalleRepository.findOne(id);
            }
        };
    }
	
	public Converter<CategoriaDetalle, String> getCategoriaDetalleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ve.com.tracking.model.CategoriaDetalle, java.lang.String>() {
            public String convert(CategoriaDetalle categoriaDetalle) {
                return new StringBuilder().append(categoriaDetalle.getNombre()).toString();
            }
        };
    }	
	
	public Converter<Boolean, String> getBooleanToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Boolean, java.lang.String>() {
			public java.lang.String convert(Boolean bool) {
				// TODO aplicar internacionalizacion si o no, yes o no etc...
				return bool ? "Si" : "No";
			}
		};
	}

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAuthoritiesToStringConverter());
        registry.addConverter(getIdToAuthoritiesConverter());
        registry.addConverter(getStringToAuthoritiesConverter());
        registry.addConverter(getAuthoritysToStringConverter());
        registry.addConverter(getIdToAuthoritysConverter());
        registry.addConverter(getStringToAuthoritysConverter());
        registry.addConverter(getUsersToStringConverter());
        registry.addConverter(getIdToUsersConverter());
        registry.addConverter(getStringToUsersConverter());
        registry.addConverter(getEmpresaEnvioToStringConverter());
        registry.addConverter(getStringToEmpresaEnvioConverter());
        registry.addConverter(getIdToEmpresaEnvioConverter());        
        registry.addConverter(getIdToCountriesConverter());
        registry.addConverter(getCountriesToStringConverter());
        registry.addConverter(getIdToPaqueteConverter());
        registry.addConverter(getStringToPaqueteConverter());
        registry.addConverter(getPaqueteToStringConverter());
        registry.addConverter(getStringToDestinosConverter());
        registry.addConverter(getIdToDestinosConverter());
        registry.addConverter(getDestinosToStringConverter());
        registry.addConverter(getStringToTipoEmbalajeConverter());
        registry.addConverter(getIdToTipoEmbalajeConverter());
        registry.addConverter(getTipoEmbalajeToStringConverter());
        registry.addConverter(getStringToCategoriaDetalleConverter());
        registry.addConverter(getIdToCategoriaDetalleConverter());
        registry.addConverter(getCategoriaDetalleToStringConverter());
        registry.addConverter(getBooleanToStringConverter());
    }
}
