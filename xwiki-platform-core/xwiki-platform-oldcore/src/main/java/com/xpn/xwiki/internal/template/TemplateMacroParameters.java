/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.xpn.xwiki.internal.template;

import org.xwiki.properties.annotation.PropertyDescription;

/**
 * Parameters for the {@link com.xpn.xwiki.internal.template.TemplateMacro} Macro.
 * 
 * @version $Id$
 * @since 6.1RC1
 */
public class TemplateMacroParameters
{
    /**
     * @see #getName()
     */
    private String name;

    /**
     * @param name the name of the template
     */
    @PropertyDescription("the name of the template")
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the name of the template
     */
    public String getName()
    {
        return this.name;
    }
}
